package com.codingwithrufat.bespeaker.features.feature_auth.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserLogin
import com.codingwithrufat.bespeaker.features.feature_auth.domain.use_case.LoginUserCase
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userCase: LoginUserCase
) : ViewModel() {

    private val _loginResponse = MutableLiveData<NetworkResponse>(NetworkResponse.LOADING())

    val loginResponse
    get() = _loginResponse

    fun loginUser(user: UserLogin) = viewModelScope.launch {

        userCase.invoke(user).collect { response ->

            _loginResponse.value = response

        }

    }

}