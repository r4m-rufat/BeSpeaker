package com.codingwithrufat.bespeaker.features.feature_home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithrufat.bespeaker.common.utils.NetworkResponse
import com.codingwithrufat.bespeaker.common.utils.UserActivity
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.CallNotification
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.HomeUsersItem
import com.codingwithrufat.bespeaker.features.feature_home.domain.use_case.HomePageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomePageUseCase
) : ViewModel() {

    private val activeUsers = MutableLiveData<NetworkResponse<List<HomeUsersItem>>>()

    val active_users: LiveData<NetworkResponse<List<HomeUsersItem>>>
        get() = activeUsers

    private val _notification = MutableLiveData<NetworkResponse<List<CallNotification>>>()

    val notification: LiveData<NetworkResponse<List<CallNotification>>>
        get() = _notification

    private val addCallToDB = MutableLiveData<NetworkResponse<Boolean>>()

    val add_call_to_db: LiveData<NetworkResponse<Boolean>>
    get() = addCallToDB

    private val removeNotification = MutableLiveData<NetworkResponse<Boolean>>()

    val remove_notification: LiveData<NetworkResponse<Boolean>>
    get() = removeNotification


    fun getActiveUsers(activity_status: UserActivity) = viewModelScope.launch {

        useCase.getActiveUsers(activity_status).collect {

            activeUsers.value = it

        }

    }

    fun getObservableNotifications() = viewModelScope.launch {

        useCase.getCallNotifications().collect {
            _notification.value = it
        }

    }

    fun callUserAndAddCallModelToDB(callUserID: String, callModel: CallNotification) = viewModelScope.launch {

        useCase.callUser(callUserID, callModel).collect {

            addCallToDB.value = it

        }

    }

    fun removeNotification(senderID: String) = viewModelScope.launch {

        useCase.deleteNotificationFromDB(senderID).collect { response ->
            removeNotification.value = response
        }

    }

}