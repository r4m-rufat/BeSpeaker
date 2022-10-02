package com.codingwithrufat.bespeaker.features.feature_auth.data.repository

import android.net.Uri
import android.util.Log
import com.codingwithrufat.bespeaker.common.TAG
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.CompleteProfile
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class CompletePorfileRepository_Impl @Inject constructor(
    private val reference: StorageReference,
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : CompleteProfile {

    override suspend fun storeImageInFirebaseStorage(file: Uri) = callbackFlow {

        reference.child("${firebaseAuth.currentUser?.uid}/${System.currentTimeMillis()}_${UUID.randomUUID()}.png/")
            .putFile(file).addOnSuccessListener { task ->

                task.storage.downloadUrl.addOnSuccessListener{

                    trySend(NetworkResponse.SUCCEED(it.toString()))
                    Log.d(TAG, "storeImageInFirebaseStorage: Image successfully loaded")

                }

            }.addOnFailureListener {

                trySend(NetworkResponse.ERROR(Exception("Image Loading: ${it.message}")))

            }.addOnProgressListener {
                val percent = ((it.bytesTransferred * 100) / it.totalByteCount).toInt()
                trySend(NetworkResponse.LOADING(percent))

            }

        awaitClose ()

    }.flowOn(IO)

    override suspend fun completeUserProfile(userRegister: UserRegister) = callbackFlow {

        val hashMap = HashMap<String, Any>()
        hashMap["birthday"] = userRegister.birthday!!
        hashMap["english_level"] = userRegister.english_level!!
        hashMap["gender"] = userRegister.gender!!
        hashMap["name"] = userRegister.name!!
        hashMap["surname"] = userRegister.surname!!
        hashMap["profile_image_link"] = userRegister.profile_image_link!!
        hashMap["complete_profile_status"] = userRegister.complete_profile_status!!

        firebaseAuth.currentUser?.let {

            trySend(NetworkResponse.LOADING())

            db.collection("users")
                .document(it.uid)
                .update(hashMap)
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

        }?:run {


            trySend(NetworkResponse.ERROR(Exception("User is null")))

        }

        awaitClose ()

    }.flowOn(IO)

}