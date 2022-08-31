package com.codingwithrufat.bespeaker.features.feature_splash.domain.use_case

import com.codingwithrufat.bespeaker.features.feature_splash.domain.repository.CheckUser
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val checkUser: CheckUser
) {

    suspend fun checkEmail() = checkUser.checkUserEmailState()

    suspend fun checkProfileStatus() = checkUser.checkUserProfileStatus()

}