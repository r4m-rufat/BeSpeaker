package com.codingwithrufat.bespeaker.features.feature_home.data.repository

import com.codingwithrufat.bespeaker.common.NetworkResponse
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.HomeUsersItem
import com.codingwithrufat.bespeaker.features.feature_home.domain.repository.HomePageRepository
import com.codingwithrufat.bespeaker.features.feature_home.util.UserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class HomePageRepository_Impl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : HomePageRepository {

    override suspend fun updateUserLastSeen(userActivity: UserActivity): Flow<NetworkResponse<Boolean>> = callbackFlow {

        val last_seen = when(userActivity) {
            is UserActivity.ONLINE -> {
                userActivity.seen
            }
            is UserActivity.OFFLINE -> {
                userActivity.seen
            }
        }

        trySend(NetworkResponse.LOADING())

        db.collection("users")
            .document(auth.currentUser!!.uid)
            .update("last_seen", last_seen)
            .addOnSuccessListener {
                trySend(NetworkResponse.SUCCEED(true))
            }.addOnFailureListener {
                trySend(NetworkResponse.ERROR(it.message!!))
            }

    }

    override suspend fun getActiveUsers(userActivity: UserActivity): Flow<NetworkResponse<List<HomeUsersItem>>> = callbackFlow {

        db.collection("users")
            .whereEqualTo("last_seen", userActivity.seen)
            .get()
            .addOnSuccessListener {
                val data = mutableListOf<HomeUsersItem>()
                for (snapshot in it.documents) {

                    data.add(
                        HomeUsersItem(
                            snapshot.getString("id"),
                            snapshot.getString("name"),
                            snapshot.getString("surname"),
                            snapshot.getString("profile_image_link"),
                            snapshot.getString("english_level")
                        )
                    )

                }
                trySend(NetworkResponse.SUCCEED(data))

            }.addOnFailureListener {
                trySend(NetworkResponse.ERROR(it.message!!))
            }


    }

}