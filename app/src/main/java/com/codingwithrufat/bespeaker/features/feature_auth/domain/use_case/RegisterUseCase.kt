package com.codingwithrufat.bespeaker.features.feature_auth.domain.use_case

import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {

    suspend operator fun invoke(user: UserRegister) =
        registerRepository.registerUser(userRegister = user)

}