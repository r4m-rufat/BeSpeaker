package com.codingwithrufat.bespeaker.domain.model

import com.codingwithrufat.bespeaker.domain.util.EnglishLevel
import com.codingwithrufat.bespeaker.domain.util.GenderType

data class Register(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val birthday: String,
    val gender: GenderType,
    val english_level: EnglishLevel,
)
