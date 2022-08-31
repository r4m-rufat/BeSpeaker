package com.codingwithrufat.bespeaker.features.feature_auth.domain.model

data class UserRegister (
    var uid: String? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var surname: String? = null,
    var birthday: String? = null,
    var gender: String? = null,
    var english_level: String? = null,
    var profile_image_link: String? = null,
    var complete_profile_status: Boolean? = null
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

    fun id(id: String): UserBuilder {
        userRegister.uid = id
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

    fun gender(gender: String): UserBuilder {
        userRegister.gender = gender
        return this
    }

    fun englishLevel(englishLevel: String): UserBuilder {
        userRegister.english_level = englishLevel
        return this
    }

    fun profileImageLink(profileImageLink: String): UserBuilder {
        userRegister.profile_image_link = profileImageLink
        return this
    }

    fun completeStatus(status: Boolean): UserBuilder {
        userRegister.complete_profile_status = status
        return this
    }

    fun build() = userRegister
}