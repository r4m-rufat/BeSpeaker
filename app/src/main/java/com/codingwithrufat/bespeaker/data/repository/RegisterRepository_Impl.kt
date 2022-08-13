package com.codingwithrufat.bespeaker.data.repository

import com.codingwithrufat.bespeaker.domain.model.Register
import com.codingwithrufat.bespeaker.domain.repository.RegisterRepository
import com.codingwithrufat.bespeaker.domain.util.NetworkResponse

class RegisterRepository_Impl: RegisterRepository {
    override suspend fun registerUser(register: Register): NetworkResponse {
        TODO("Not yet implemented")
    }
}