package com.codingwithrufat.bespeaker.common.utils

sealed class UserActivity(val seen: String) {

    data class ONLINE(val online: String): UserActivity(online)
    data class OFFLINE(val date: String): UserActivity(date)

}