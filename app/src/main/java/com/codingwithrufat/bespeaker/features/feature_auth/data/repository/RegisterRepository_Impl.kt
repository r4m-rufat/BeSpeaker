package com.codingwithrufat.bespeaker.features.feature_auth.data.repository

import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.RegisterRepository
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterRepository_Impl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : RegisterRepository {

    override suspend fun registerUser(userRegister: UserRegister) = callbackFlow {

        firebaseAuth.createUserWithEmailAndPassword(
            userRegister.email!!,
            userRegister.password!!
        ).addOnSuccessListener {

            trySend(NetworkResponse.LOADING())

            firebaseAuth.addAuthStateListener(object : FirebaseAuth.AuthStateListener {
                override fun onAuthStateChanged(auth: FirebaseAuth) {

                    auth.currentUser?.let { user ->

                        user.sendEmailVerification().addOnCompleteListener {

                            if (it.isSuccessful) {

                                userRegister.uid = user.uid

                                db.collection("users")
                                    .document(user.uid)
                                    .set(userRegister)
                                    .addOnCompleteListener { task ->

                                        if (task.isSuccessful) {
                                            trySend(NetworkResponse.SUCCEED(user.email))
                                        } else {
                                            task.exception?.let { exception ->
                                                trySend(NetworkResponse.ERROR(Exception("Firestore Complete Exception: ${exception.message}")))
                                            }
                                        }

                                    }.addOnFailureListener { exception ->

                                        trySend(NetworkResponse.ERROR(Exception("Firestore Exception: ${exception.message}")))

                                    }


                            }

                        }.addOnFailureListener {

                            trySend(NetworkResponse.ERROR(Exception("Send Mail Exception: ${it.message}")))

                        }

                    }

                }

            })


        }.addOnFailureListener {
            trySend(NetworkResponse.ERROR(Exception("Authentication Exception: ${it.message}")))
        }

        awaitClose()

    }.flowOn(IO)

}