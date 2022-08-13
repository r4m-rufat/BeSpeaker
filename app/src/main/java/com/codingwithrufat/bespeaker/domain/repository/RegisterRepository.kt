package com.codingwithrufat.bespeaker.domain.repository

import com.codingwithrufat.bespeaker.domain.model.Register
import com.codingwithrufat.bespeaker.domain.util.NetworkResponse

interface RegisterRepository {
    suspend fun registerUser(register: Register): NetworkResponse
}