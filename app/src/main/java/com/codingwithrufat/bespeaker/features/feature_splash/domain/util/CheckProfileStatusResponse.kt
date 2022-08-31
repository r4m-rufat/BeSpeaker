package com.codingwithrufat.bespeaker.features.feature_splash.domain.util

sealed interface CheckProfileStatusResponse {

    object ProfileIsCompleted: CheckProfileStatusResponse
    object ProfileIsNotCompleted: CheckProfileStatusResponse
    data class ERROR(val err_msg: Throwable): CheckProfileStatusResponse

}