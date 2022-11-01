package com.codingwithrufat.bespeaker.features.feature_home.domain.model

import com.codingwithrufat.bespeaker.common.utils.CallType
import com.codingwithrufat.bespeaker.common.utils.EnglishLevel

data class CallNotification(
    val notification_id: String? = null,
    val receiver_id: String? = null,
    val sender_id: String? = null,
    val sender_name: String? = null,
    val sender_english_level: EnglishLevel? = null,
    val call_type: CallType? = null,
    val sender_image_url: String? = null,
)
