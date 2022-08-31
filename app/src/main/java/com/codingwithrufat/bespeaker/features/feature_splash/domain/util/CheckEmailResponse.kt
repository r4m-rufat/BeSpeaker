package com.codingwithrufat.bespeaker.features.feature_splash.domain.util

sealed interface CheckEmailResponse{

    object EmailIsVerified: CheckEmailResponse
    object EmailIsNotVerified: CheckEmailResponse
    object NoAnyUser: CheckEmailResponse

}