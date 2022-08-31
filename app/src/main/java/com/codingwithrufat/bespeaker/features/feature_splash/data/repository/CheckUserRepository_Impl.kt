package com.codingwithrufat.bespeaker.features.feature_splash.data.repository

import com.codingwithrufat.bespeaker.features.feature_splash.domain.repository.CheckUser
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckEmailResponse
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckProfileStatusResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckUserRepository_Impl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): CheckUser {

    override suspend fun checkUserEmailState() = flow{

        firebaseAuth.currentUser?.let { user ->

            if (user.isEmailVerified)
                emit(CheckEmailResponse.EmailIsVerified)
            else
                emit(CheckEmailResponse.EmailIsNotVerified)


        } ?: emit(CheckEmailResponse.NoAnyUser)

    }.flowOn(IO)

    override suspend fun checkUserProfileStatus() = callbackFlow {

        firebaseAuth.currentUser?.let { user ->
            db.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener {

                    it.getBoolean("complete_profile_status")?.let { profile_status ->
                        if (profile_status)
                            trySend(CheckProfileStatusResponse.ProfileIsCompleted)
                        else
                            trySend(CheckProfileStatusResponse.ProfileIsNotCompleted)
                    }

                }.addOnFailureListener { exception ->
                    trySend(CheckProfileStatusResponse.ERROR(exception))
                }

        }
    }.flowOn(IO)


}