package com.codingwithrufat.bespeaker.common.services

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.work.*
import com.codingwithrufat.bespeaker.common.utils.TAG
import com.codingwithrufat.bespeaker.common.utils.UPDATE_SEEN_UNIQUE_WORK_NAME
import com.codingwithrufat.bespeaker.common.utils.UserActivity
import com.codingwithrufat.bespeaker.common.utils.WORK_MSG

object ServiceManager {

    fun updateLastSeenWithWorker(activity: FragmentActivity, userActivity: UserActivity) {

        val lastSeen = when (userActivity) {
            is UserActivity.ONLINE -> {
                userActivity.seen
            }
            is UserActivity.OFFLINE -> {
                userActivity.seen
            }
        }

        val data = Data.Builder()
            .putString("user_activity", lastSeen)
            .build()
        val workManager = WorkManager.getInstance(activity)

        val seenWorker = OneTimeWorkRequestBuilder<UpdateLastSeenWorkManager>()
            .setInputData(data)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()

        workManager.beginUniqueWork(
            UPDATE_SEEN_UNIQUE_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            seenWorker
        ).enqueue()

        workManager.getWorkInfosForUniqueWorkLiveData(UPDATE_SEEN_UNIQUE_WORK_NAME)
            .observe(activity) { info ->

                val workInfo = info.find { it.id == seenWorker.id }

                workInfo?.let {

                    Log.d(TAG, "updateLastSeenWithWorker: ${it.outputData.getString(WORK_MSG)}")

                }
            }
    }

}