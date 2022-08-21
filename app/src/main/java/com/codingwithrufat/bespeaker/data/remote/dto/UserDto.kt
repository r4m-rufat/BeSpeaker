package com.codingwithrufat.bespeaker.data.remote.dto

import com.codingwithrufat.bespeaker.domain.model.UserLogin
import com.codingwithrufat.bespeaker.domain.util.EnglishLevel
import com.codingwithrufat.bespeaker.domain.util.GenderType

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