package com.codingwithrufat.bespeaker.common.services

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.codingwithrufat.bespeaker.common.utils.TAG
import com.codingwithrufat.bespeaker.common.utils.UserActivity
import com.codingwithrufat.bespeaker.common.utils.WORK_MSG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class UpdateLastSeenWorkManager constructor(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun doWork(): Result {

        val lastSeen = inputData.getString("user_activity")

        db.collection("users")
            .document(auth.currentUser!!.uid)
            .update("last_seen", lastSeen)
            .addOnSuccessListener {
                Result.success(workDataOf(WORK_MSG to "Last seen successfully updated"))
            }.addOnFailureListener {
                Result.failure(workDataOf(WORK_MSG to it.message))
            }

        return Result.failure(workDataOf(WORK_MSG to "Process starts"))

    }

}