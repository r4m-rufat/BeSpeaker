package com.codingwithrufat.bespeaker.features.feature_home.domain.use_case

import com.codingwithrufat.bespeaker.features.feature_home.domain.repository.HomePageRepository
import com.codingwithrufat.bespeaker.features.feature_home.util.UserActivity
import javax.inject.Inject

class HomePageUseCase @Inject constructor(
    private val repository: HomePageRepository
) {

    private suspend fun updateLastSeen(activity_status: UserActivity) = repository.updateUserLastSeen(activity_status)

    private suspend fun getActiveUsers(activity_status: UserActivity) = repository.getActiveUsers(activity_status)

}