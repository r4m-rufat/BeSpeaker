package com.codingwithrufat.bespeaker.features.feature_auth.data.repository

import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.RegisterRepository
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterRepository_Impl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : RegisterRepository {

    override suspend fun registerUser(userRegister: UserRegister) = callbackFlow {

        var callback = firebaseAuth.createUserWithEmailAndPassword(
            userRegister.email!!,
            userRegister.password!!
        ).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                trySend(NetworkResponse.SUCCEED())
            } else {
                task.exception?.let {
                    trySend(NetworkResponse.ERROR(it))
                }
            }

        }.addOnFailureListener {
            trySend(NetworkResponse.ERROR(it))
        }

        awaitClose {
            //TODO unsubscribe listener
        }

    }.flowOn(IO)

}