package com.codingwithrufat.bespeaker.features.feature_splash.data.repository

import android.util.Log
import com.codingwithrufat.bespeaker.common.utils.TAG
import com.codingwithrufat.bespeaker.features.feature_splash.domain.repository.CheckUser
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckEmailResponse
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckProfileStatusResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckUserRepository_Impl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): CheckUser {

    override suspend fun checkUserEmailState() = callbackFlow{

        firebaseAuth.currentUser?.let { user ->

            user.reload().addOnSuccessListener {
                if (user.isEmailVerified)
                    trySend(CheckEmailResponse.EmailIsVerified)
                else
                    trySend(CheckEmailResponse.EmailIsNotVerified)

            }.addOnFailureListener {
                trySend(CheckEmailResponse.EmailIsNotVerified)
            }


        } ?: trySend(CheckEmailResponse.NoAnyUser)

        awaitClose()

    }.flowOn(IO)

    override suspend fun checkUserProfileStatus() = callbackFlow {

        firebaseAuth.currentUser?.let { user ->

            db.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener {

                    if (it.getBoolean("complete_profile_status") != null){
                        if (it.getBoolean("complete_profile_status")!!)
                            trySend(CheckProfileStatusResponse.ProfileIsCompleted)
                        else
                            trySend(CheckProfileStatusResponse.ProfileIsNotCompleted)

                        Log.d(TAG, "checkUserProfileStatus: ${it.getBoolean("complete_profile_status")}")
                    }

                }.addOnFailureListener { exception ->
                    trySend(CheckProfileStatusResponse.ERROR(exception))
                }

        }
        awaitClose()
    }.flowOn(IO)


}