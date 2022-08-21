package com.codingwithrufat.bespeaker.domain.model

import com.codingwithrufat.bespeaker.domain.util.EnglishLevel
import com.codingwithrufat.bespeaker.domain.util.GenderType

data class UserLogin(
    val email: String,
    val password: String,
)
