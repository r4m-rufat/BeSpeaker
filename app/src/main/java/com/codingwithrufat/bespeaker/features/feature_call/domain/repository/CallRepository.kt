package com.codingwithrufat.bespeaker.features.feature_call.domain.repository

import com.codingwithrufat.bespeaker.common.utils.NetworkResponse
import com.codingwithrufat.bespeaker.features.feature_call.data.model.CallDetailResponse
import com.codingwithrufat.bespeaker.common.utils.CallState
import kotlinx.coroutines.flow.Flow

interface CallRepository {

    suspend fun updateVideoCallFieldInDb(callState: CallState): Flow<NetworkResponse<Boolean>>

    suspend fun addVideoCallDetailToDb(callDetailResponse: CallDetailResponse): Flow<NetworkResponse<Boolean>>

}