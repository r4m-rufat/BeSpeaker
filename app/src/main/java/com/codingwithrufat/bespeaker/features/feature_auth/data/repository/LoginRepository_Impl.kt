package com.codingwithrufat.bespeaker.features.feature_auth.data.repository

import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserLogin
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.LoginRepository
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository_Impl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): LoginRepository{

    override suspend fun login(login: UserLogin): Flow<NetworkResponse> = callbackFlow {

        auth.uid?.let {

            db.collection("users").whereEqualTo("email", login.email)
                .whereEqualTo("password", login.password)
                .get()
                .addOnSuccessListener {

                    trySend(NetworkResponse.SUCCEED())

                }.addOnFailureListener {
                    trySend(NetworkResponse.ERROR(it))
                }

        }.run {

            trySend(NetworkResponse.ERROR(NullPointerException("User doesn't exist")))

        }

        awaitClose ()


    }

}