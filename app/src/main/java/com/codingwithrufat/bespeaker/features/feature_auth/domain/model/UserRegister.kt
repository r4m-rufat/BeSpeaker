package com.codingwithrufat.bespeaker.features.feature_auth.domain.model

import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.EnglishLevel
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.GenderType

data class UserRegister (
    var id: Int? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var surname: String? = null,
    var birthday: String? = null,
    var gender: GenderType? = null,
    var english_level: EnglishLevel? = null,
) {
    companion object {
        fun Builder(): UserBuilder = UserBuilder()
    }
}

/**
 * Builder Pattern
 */
class UserBuilder {

    private var userRegister: UserRegister = UserRegister()

    fun id(id: Int): UserBuilder {
        userRegister.id = id
        return this
    }

    fun email(email: String): UserBuilder {
        userRegister.email = email
        return this
    }

    fun password(password: String): UserBuilder {
        userRegister.password = password
        return this
    }

    fun name(name: String): UserBuilder {
        userRegister.name = name
        return this
    }

    fun surname(surname: String): UserBuilder {
        userRegister.surname = surname
        return this
    }

    fun birthday(birthday: String): UserBuilder {
        userRegister.birthday = birthday
        return this
    }

    fun gender(gender: GenderType): UserBuilder {
        userRegister.gender = gender
        return this
    }

    fun englishLevel(englishLevel: EnglishLevel): UserBuilder {
        userRegister.english_level = englishLevel
        return this
    }

    fun build() = userRegister
}