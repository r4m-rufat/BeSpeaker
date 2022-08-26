package com.codingwithrufat.bespeaker.features.feature_auth.domain.util

sealed interface NetworkResponse {
    data class SUCCEED(val value: Any? = null): NetworkResponse
    data class ERROR(val error_msg: Throwable): NetworkResponse
    data class LOADING(val percent: Int? = null): NetworkResponse // it is needed in some cases (image uploading)
}