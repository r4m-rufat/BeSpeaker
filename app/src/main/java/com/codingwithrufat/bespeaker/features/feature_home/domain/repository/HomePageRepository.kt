package com.codingwithrufat.bespeaker.features.feature_home.domain.repository

import com.codingwithrufat.bespeaker.common.NetworkResponse
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.HomeUsersItem
import com.codingwithrufat.bespeaker.features.feature_home.util.UserActivity
import kotlinx.coroutines.flow.Flow

interface HomePageRepository {

    suspend fun updateUserLastSeen(userActivity: UserActivity): Flow<NetworkResponse<Boolean>>


    // this functions is used for online users
    suspend fun getActiveUsers(userActivity: UserActivity): Flow<NetworkResponse<List<HomeUsersItem>>>

}