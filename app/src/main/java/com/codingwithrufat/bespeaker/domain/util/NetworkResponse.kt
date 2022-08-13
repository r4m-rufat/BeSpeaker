package com.codingwithrufat.bespeaker.domain.util

sealed interface NetworkResponse {
    object SUCCEED: NetworkResponse
    data class ERROR(val error_msg: String): NetworkResponse
    data class LOADING(var loading: Boolean = true): NetworkResponse
}