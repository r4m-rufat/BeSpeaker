package com.codingwithrufat.bespeaker.features.feature_auth.domain.repository

import android.net.Uri
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface CompleteProfile {

    suspend fun storeImageInFirebaseStorage(file: Uri): Flow<NetworkResponse>

    suspend fun completeUserProfile(userRegister: UserRegister): Flow<NetworkResponse>

}