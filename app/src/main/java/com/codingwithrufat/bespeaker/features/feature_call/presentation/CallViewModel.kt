package com.codingwithrufat.bespeaker.features.feature_call.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithrufat.bespeaker.common.utils.CallState
import com.codingwithrufat.bespeaker.common.utils.NetworkResponse
import com.codingwithrufat.bespeaker.features.feature_call.data.model.CallDetailResponse
import com.codingwithrufat.bespeaker.features.feature_call.domain.use_case.CallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallViewModel @Inject constructor(
    private val useCase: CallUseCase
) : ViewModel() {

    private val _updateVideoCallField = MutableLiveData<NetworkResponse<Boolean>>()

    val updateVideoCallField: LiveData<NetworkResponse<Boolean>>
        get() = _updateVideoCallField

    private val _addCallDetail = MutableLiveData<NetworkResponse<Boolean>>()

    val addCallDetail: LiveData<NetworkResponse<Boolean>>
        get() = _addCallDetail

    fun updateField(state: CallState) = viewModelScope.launch {

        useCase.updateVideoCallFieldInDb(state).collect {

            _updateVideoCallField.value = it

        }

    }

    fun addCallDetail(model: CallDetailResponse) = viewModelScope.launch {

        useCase.addVideoCallDetailToDb(model).collect {

            _addCallDetail.value = it

        }

    }

}