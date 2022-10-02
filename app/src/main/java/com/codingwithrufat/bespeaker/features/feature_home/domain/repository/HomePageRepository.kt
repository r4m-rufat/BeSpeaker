package com.codingwithrufat.bespeaker.features.feature_home.domain.repository

import com.codingwithrufat.bespeaker.common.NetworkResponse
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.HomeUsersItem
import kotlinx.coroutines.flow.Flow

interface HomePageRepository {

    fun updateUserLastSeen(): Flow<NetworkResponse<Boolean>>

    fun getAllUsers(): Flow<NetworkResponse<HomeUsersItem>>

}