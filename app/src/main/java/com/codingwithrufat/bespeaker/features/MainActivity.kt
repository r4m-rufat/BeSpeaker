package com.codingwithrufat.bespeaker.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codingwithrufat.bespeaker.common.services.ServiceManager
import com.codingwithrufat.bespeaker.common.utils.TAG
import com.codingwithrufat.bespeaker.common.utils.UserActivity
import com.codingwithrufat.bespeaker.common.utils.getCurrentTime
import com.codingwithrufat.bespeaker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onPause() {
        super.onPause()
        ServiceManager.updateLastSeenWithWorker(this, UserActivity.OFFLINE(getCurrentTime()))
    }

}