package com.codingwithrufat.bespeaker.data.repository

import com.codingwithrufat.bespeaker.domain.model.UserRegister
import com.codingwithrufat.bespeaker.domain.repository.RegisterRepository
import com.codingwithrufat.bespeaker.domain.util.NetworkResponse
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

        val callback = firebaseAuth.createUserWithEmailAndPassword(userRegister.email!!, userRegister.password!!).addOnCompleteListener { task ->

            if (task.isSuccessful){
                trySend(NetworkResponse.SUCCEED)
            }else {
                task.exception?.let {
                    trySend(NetworkResponse.ERROR(it))
                }
            }

        }.addOnFailureListener {
            trySend(NetworkResponse.ERROR(it))
        }

        awaitClose {
            callback.isCanceled
        }

    }.flowOn(IO)

}