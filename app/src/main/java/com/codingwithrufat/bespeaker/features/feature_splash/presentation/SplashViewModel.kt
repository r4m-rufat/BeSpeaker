package com.codingwithrufat.bespeaker.features.feature_splash.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithrufat.bespeaker.features.feature_splash.domain.use_case.SplashUseCase
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckEmailResponse
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckProfileStatusResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: SplashUseCase
): ViewModel() {

    private val _emailVerified = MutableLiveData<CheckEmailResponse>()

    val emailVerified
    get(): LiveData<CheckEmailResponse> = _emailVerified

    private val _profileStatus = MutableLiveData<CheckProfileStatusResponse>()

    val profileStatus
        get(): LiveData<CheckProfileStatusResponse> = _profileStatus

    // user verified his/her profile or not
    fun checkEmailVerifiedOrNot() = viewModelScope.launch {

        useCase.checkEmail().collect{
            _emailVerified.value = it
        }

    }

    // user completed your profile or not
    fun checkUserProfileStatus() = viewModelScope.launch {

        useCase.checkProfileStatus().collect{
            _profileStatus.value = it
        }

    }


}