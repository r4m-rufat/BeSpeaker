package com.codingwithrufat.bespeaker.features.feature_auth.presentation.complete_profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.use_case.CompleteUserProfileUseCase
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteProfileViewModel @Inject constructor(
    private val useCase: CompleteUserProfileUseCase
) : ViewModel() {

    private val _imageAndCompleteProfileState =
        MutableLiveData<NetworkResponse>()

    val imageAndCompleteProfileState
        get() = _imageAndCompleteProfileState

    /**
     * we have two multiple network call state in one Network Response sequentially
     * @param completeProfile()
     * @param loadImageToDb()
     */

    fun completeProfile(userRegister: UserRegister) = viewModelScope.launch {

        useCase.executeCompleteProfile(userRegister).collect { response ->

            _imageAndCompleteProfileState.value = response

        }

    }

    fun loadImageToDB(uri: Uri) = viewModelScope.launch {

        useCase.executeStoreImage(file_uri = uri).collect { response ->

            _imageAndCompleteProfileState.value = response

        }

    }


}