package com.codingwithrufat.bespeaker.features.feature_auth.data.remote.dto

import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.EnglishLevel
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.GenderType

class UserDto(
    val uid: String,
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val birthday: String,
    val gender: GenderType,
    val english_level: EnglishLevel,
    var profile_image_link: String,
    var complete_profile_status: Boolean
)