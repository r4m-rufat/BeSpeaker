package com.codingwithrufat.bespeaker.domain.use_case

import com.codingwithrufat.bespeaker.domain.model.UserRegister
import com.codingwithrufat.bespeaker.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {

    suspend operator fun invoke(user: UserRegister) =
        registerRepository.registerUser(userRegister = user)

}