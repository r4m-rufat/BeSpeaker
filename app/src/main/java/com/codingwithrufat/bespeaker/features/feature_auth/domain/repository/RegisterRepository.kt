package com.codingwithrufat.bespeaker.features.feature_auth.domain.repository

import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface RegisterRepository  {
    suspend fun registerUser(userRegister: UserRegister): Flow<NetworkResponse>
}