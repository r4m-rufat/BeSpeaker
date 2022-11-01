package com.codingwithrufat.bespeaker.features.feature_home.data.repository

import com.codingwithrufat.bespeaker.common.utils.NetworkResponse
import com.codingwithrufat.bespeaker.common.utils.UserActivity
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.CallNotification
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.HomeUsersItem
import com.codingwithrufat.bespeaker.features.feature_home.domain.repository.HomePageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomePageRepository_Impl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fs: FirebaseFirestore,
    private val db: FirebaseDatabase
) : HomePageRepository {

    override suspend fun getActiveUsers(userActivity: UserActivity) = callbackFlow {

        fs.collection("users")
            .whereEqualTo("last_seen", userActivity.seen)
            .get()
            .addOnSuccessListener {
                val data = mutableListOf<HomeUsersItem>()
                for (snapshot in it.documents) {
                    if (snapshot.getString("uid")!! != auth.currentUser!!.uid) {
                        data.add(
                            HomeUsersItem(
                                snapshot.getString("uid"),
                                snapshot.getString("name"),
                                snapshot.getString("surname"),
                                snapshot.getString("profile_image_link"),
                                snapshot.getString("english_level")
                            )
                        )
                    }

                }
                trySend(NetworkResponse.SUCCEED<List<HomeUsersItem>>(data))

            }.addOnFailureListener {
                trySend(NetworkResponse.ERROR<List<HomeUsersItem>>(it.message!!))
            }

        awaitClose()

    }.flowOn(IO)

    override suspend fun observeNotifications() = callbackFlow {

        trySend(NetworkResponse.LOADING())

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nots: MutableList<CallNotification> = mutableListOf()
                for (singleSnapshot in snapshot.children) {
                    nots.add(singleSnapshot.getValue(CallNotification::class.java)!!)
                }
                trySend(NetworkResponse.SUCCEED(nots))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(NetworkResponse.ERROR(error.message))
            }
        }

        val child = db.getReference("calls/${auth.currentUser!!.uid}")

        child.addValueEventListener(listener)

        awaitClose {
            child.removeEventListener(listener)
        }

    }.flowOn(IO)

    override suspend fun callUserAndAddToDB(
        calledUserID: String,
        callNotification: CallNotification
    ) = callbackFlow {

        trySend(NetworkResponse.LOADING<Boolean>())

        db.getReference("calls/$calledUserID/${callNotification.receiver_id}")
            .setValue(callNotification)
            .addOnSuccessListener {
                trySend(NetworkResponse.SUCCEED(true))
            }.addOnFailureListener {
                trySend(NetworkResponse.ERROR<Boolean>(it.message!!))
            }

        awaitClose()

    }.flowOn(IO)

    override suspend fun deleteNotificationFromSpecificUserDB(senderID: String) = callbackFlow {
        db.getReference("calls/${auth.currentUser?.uid}/$senderID")
            .removeValue()
            .addOnSuccessListener {
                trySend(NetworkResponse.SUCCEED(true))
            }.addOnFailureListener {
                trySend(NetworkResponse.ERROR<Boolean>(it.message!!))
            }

        awaitClose()
    }

}