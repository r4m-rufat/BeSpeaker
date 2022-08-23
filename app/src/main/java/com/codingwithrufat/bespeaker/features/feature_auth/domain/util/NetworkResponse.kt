package com.codingwithrufat.bespeaker.features.feature_auth.domain.util

sealed interface NetworkResponse {
    object SUCCEED: NetworkResponse
    data class ERROR(val error_msg: Throwable): NetworkResponse
    data class LOADING(var loading: Boolean = true): NetworkResponse
}