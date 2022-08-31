package com.codingwithrufat.bespeaker.features.feature_auth.domain.repository

import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserLogin
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(login: UserLogin): Flow<NetworkResponse>

}