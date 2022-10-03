package com.codingwithrufat.bespeaker.features.feature_home.util

sealed class UserActivity(val seen: String) {

    data class ONLINE(val online: String): UserActivity(online)
    data class OFFLINE(val date: String): UserActivity(date)

}