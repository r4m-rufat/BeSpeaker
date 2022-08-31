package com.codingwithrufat.bespeaker.features.feature_splash.domain.repository

import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckEmailResponse
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckProfileStatusResponse
import kotlinx.coroutines.flow.Flow

interface CheckUser {

    suspend fun checkUserEmailState(): Flow<CheckEmailResponse>

    suspend fun checkUserProfileStatus(): Flow<CheckProfileStatusResponse>

}