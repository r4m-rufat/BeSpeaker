package com.codingwithrufat.bespeaker.features.feature_call.data.model

import com.codingwithrufat.bespeaker.common.utils.CallType

data class CallDetailResponse(
    var id: String,
    var caller_id: String,
    var call_receiver_id: String,
    var caller_name: String,
    var call_receiver_name: String,
    var call_type: CallType,
    var call_time: String,
    var end_time: String? = null,
)