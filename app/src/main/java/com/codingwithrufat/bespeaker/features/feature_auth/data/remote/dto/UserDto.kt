package com.codingwithrufat.bespeaker.features.feature_auth.data.remote.dto

import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserLogin
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.EnglishLevel
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.GenderType

class UserDto(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val birthday: String,
    val gender: GenderType,
    val english_level: EnglishLevel,
)

fun UserDto.toUser(): UserLogin {
    return UserLogin(
        email = email,
        password = password
    )
}