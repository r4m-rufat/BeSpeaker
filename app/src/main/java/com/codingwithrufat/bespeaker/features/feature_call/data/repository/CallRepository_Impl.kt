package com.codingwithrufat.bespeaker.features.feature_call.data.repository

import com.codingwithrufat.bespeaker.common.utils.NetworkResponse
import com.codingwithrufat.bespeaker.features.feature_call.data.model.CallDetailResponse
import com.codingwithrufat.bespeaker.features.feature_call.domain.repository.CallRepository
import com.codingwithrufat.bespeaker.common.utils.CallState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CallRepository_Impl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val db: FirebaseDatabase
): CallRepository {

    override suspend fun updateVideoCallFieldInDb(callState: CallState) = callbackFlow {

        trySend(NetworkResponse.LOADING())

        auth.currentUser?.let {

            firestore.collection("users")
                .document(it.uid)
                .update("call_state", callState)
                .addOnSuccessListener {
                    trySend(NetworkResponse.SUCCEED(true))
                }.addOnFailureListener { exception ->
                    trySend(NetworkResponse.ERROR<Boolean>(exception.message!!))
                }

        }

        awaitClose()

    }.flowOn(IO)

    override suspend fun addVideoCallDetailToDb(callDetailResponse: CallDetailResponse) = callbackFlow {

        trySend(NetworkResponse.LOADING())

        db.getReference("Calls")
            .setValue(callDetailResponse)
            .addOnSuccessListener {
                trySend(NetworkResponse.SUCCEED<Boolean>(true))
            }.addOnFailureListener {
                trySend(NetworkResponse.ERROR<Boolean>(it.message!!))
            }

        awaitClose ()

    }.flowOn(IO)

}