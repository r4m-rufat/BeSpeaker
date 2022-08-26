package com.codingwithrufat.bespeaker.features.feature_auth.domain.use_case

import android.net.Uri
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.CompleteProfile
import javax.inject.Inject

class CompleteUserProfileUseCase @Inject constructor(
    private val completeProfile: CompleteProfile
) {

    suspend fun executeCompleteProfile(userRegister: UserRegister) = completeProfile.completeUserProfile(userRegister)

    suspend fun executeStoreImage(file_uri: Uri) = completeProfile.storeImageInFirebaseStorage(file_uri)

}