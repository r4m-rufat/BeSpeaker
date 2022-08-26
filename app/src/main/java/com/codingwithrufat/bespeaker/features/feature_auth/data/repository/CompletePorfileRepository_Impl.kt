package com.codingwithrufat.bespeaker.features.feature_auth.data.repository

import android.net.Uri
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.CompleteProfile
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class CompletePorfileRepository_Impl @Inject constructor(
    private val reference: StorageReference,
    private val user: FirebaseUser? = null,
    private val db: FirebaseFirestore
) : CompleteProfile {

    override suspend fun storeImageInFirebaseStorage(file: Uri) = callbackFlow {

        reference.child("${user?.uid}/${System.currentTimeMillis()}_${UUID.randomUUID()}.png/")
            .putFile(file).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val uri = task.result.storage.downloadUrl
                    trySend(NetworkResponse.SUCCEED(uri.toString()))
                } else
                    task.exception?.let {
                        trySend(NetworkResponse.ERROR(it))
                    }

            }.addOnFailureListener {

                trySend(NetworkResponse.ERROR(it))

            }.addOnProgressListener {
                val percent = ((it.bytesTransferred * 100) / it.totalByteCount).toInt()
                trySend(NetworkResponse.LOADING(percent))
            }

    }.flowOn(IO)

    override suspend fun completeUserProfile(userRegister: UserRegister) = callbackFlow {

        user?.let {

            db.collection("users")
                .document(it.uid)
                .set(userRegister)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful)
                        trySend(NetworkResponse.SUCCEED())
                    else
                        task.exception?.let { exception ->
                            trySend(NetworkResponse.ERROR(exception))
                        }

                }.addOnFailureListener { exception ->

                    trySend(NetworkResponse.ERROR(exception))

                }

        }

    }.flowOn(IO)

}