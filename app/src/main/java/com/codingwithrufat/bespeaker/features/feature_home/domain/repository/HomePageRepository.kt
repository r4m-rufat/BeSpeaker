package com.codingwithrufat.bespeaker.features.feature_home.domain.repository

import com.codingwithrufat.bespeaker.common.utils.NetworkResponse
import com.codingwithrufat.bespeaker.common.utils.UserActivity
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.CallNotification
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.HomeUsersItem
import kotlinx.coroutines.flow.Flow

interface HomePageRepository {

    // this functions is used for online users
    suspend fun getActiveUsers(userActivity: UserActivity): Flow<NetworkResponse<List<HomeUsersItem>>>

    suspend fun observeNotifications(): Flow<NetworkResponse<List<CallNotification>>>

    suspend fun callUserAndAddToDB(
        calledUserID: String,
        callNotification: CallNotification
    ): Flow<NetworkResponse<Boolean>>

    suspend fun deleteNotificationFromSpecificUserDB(
        senderID: String
    ): Flow<NetworkResponse<Boolean>>

}