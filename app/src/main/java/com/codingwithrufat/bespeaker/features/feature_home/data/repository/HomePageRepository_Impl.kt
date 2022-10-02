package com.codingwithrufat.bespeaker.features.feature_home.data.repository

import com.codingwithrufat.bespeaker.common.NetworkResponse
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.HomeUsersItem
import com.codingwithrufat.bespeaker.features.feature_home.domain.repository.HomePageRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class HomePageRepository_Impl @Inject constructor(
    private val db: FirebaseFirestore
) : HomePageRepository {

    override fun updateUserLastSeen(): Flow<NetworkResponse<Boolean>> = callbackFlow {


    }

    override fun getAllUsers(): Flow<NetworkResponse<HomeUsersItem>> = callbackFlow {


    }
}