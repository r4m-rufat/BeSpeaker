package com.codingwithrufat.bespeaker.features.feature_home.domain.use_case

import com.codingwithrufat.bespeaker.common.utils.UserActivity
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.CallNotification
import com.codingwithrufat.bespeaker.features.feature_home.domain.repository.HomePageRepository
import javax.inject.Inject

class HomePageUseCase @Inject constructor(
    private val repository: HomePageRepository
) {

    suspend fun getActiveUsers(activity_status: UserActivity) =
        repository.getActiveUsers(activity_status)

    suspend fun getCallNotifications() = repository.observeNotifications()


    suspend fun callUser(calledUserID: String, callModel: CallNotification) =
        repository.callUserAndAddToDB(calledUserID, callModel)

    suspend fun deleteNotificationFromDB(senderID: String) =
        repository.deleteNotificationFromSpecificUserDB(senderID)

}