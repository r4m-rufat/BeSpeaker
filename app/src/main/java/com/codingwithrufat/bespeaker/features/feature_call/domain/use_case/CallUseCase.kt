package com.codingwithrufat.bespeaker.features.feature_call.domain.use_case

import com.codingwithrufat.bespeaker.common.utils.CallState
import com.codingwithrufat.bespeaker.features.feature_call.data.model.CallDetailResponse
import com.codingwithrufat.bespeaker.features.feature_call.domain.repository.CallRepository
import javax.inject.Inject

class CallUseCase @Inject constructor(
    private val repository: CallRepository
) {

    suspend fun updateVideoCallFieldInDb(callState: CallState) = repository.updateVideoCallFieldInDb(callState)

    suspend fun addVideoCallDetailToDb(call_detail: CallDetailResponse) = repository.addVideoCallDetailToDb(call_detail)

}