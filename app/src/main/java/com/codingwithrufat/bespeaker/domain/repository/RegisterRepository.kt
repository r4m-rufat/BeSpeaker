package com.codingwithrufat.bespeaker.domain.repository

import com.codingwithrufat.bespeaker.domain.model.UserLogin
import com.codingwithrufat.bespeaker.domain.model.UserRegister
import com.codingwithrufat.bespeaker.domain.util.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RegisterRepository  {
    suspend fun registerUser(userRegister: UserRegister): Flow<NetworkResponse>
}