package com.codingwithrufat.bespeaker.features.feature_auth.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.use_case.RegisterUseCase
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private var _register = MutableLiveData<NetworkResponse>()

    val observeRegisterCase: LiveData<NetworkResponse>
    get() = _register

    fun register(userRegister: UserRegister) {
        viewModelScope.launch {
            registerUseCase.invoke(userRegister).collect {
                _register.value = it
            }
        }
    }

}