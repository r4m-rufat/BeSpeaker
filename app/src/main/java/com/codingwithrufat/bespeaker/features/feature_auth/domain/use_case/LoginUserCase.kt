package com.codingwithrufat.bespeaker.features.feature_auth.domain.use_case

import com.codingwithrufat.bespeaker.features.feature_auth.data.repository.LoginRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserLogin
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUserCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(user: UserLogin) = loginRepository.login(user)

}