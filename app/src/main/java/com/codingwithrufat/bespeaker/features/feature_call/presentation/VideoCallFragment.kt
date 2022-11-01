package com.codingwithrufat.bespeaker.features.feature_call.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.codingwithrufat.bespeaker.R
import com.codingwithrufat.bespeaker.common.utils.*
import com.codingwithrufat.bespeaker.databinding.FragmentVideoCallBinding
import com.codingwithrufat.bespeaker.features.feature_call.data.model.CallDetailResponse
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.agora.rtc2.*
import io.agora.rtc2.video.VideoCanvas
import kotlinx.coroutines.launch

private const val TAG = "VideoCallFragment"

@AndroidEntryPoint
class VideoCallFragment : Fragment() {

    private var _binding: FragmentVideoCallBinding? = null

    private val binding
        get() = _binding as FragmentVideoCallBinding

    private val viewModel by viewModels<CallViewModel>()

    private var callReceiverId: String = ""
    private var callReceiverName: String = ""
    private var callerName: String = ""
    private lateinit var baseContext: Context

    private var uid: Int = 100
    private lateinit var agoraEngine: RtcEngine
    private lateinit var localSurfaceView: SurfaceView
    private lateinit var remoteSurfaceView: SurfaceView
    private lateinit var CHANNEL_NAME: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            callerName = it.getString("caller_name")!!
            callReceiverName = it.getString("call_receiver_name")!!
            callReceiverId = it.getString("call_receiver_id")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVideoCallBinding.inflate(layoutInflater)

        baseContext = requireContext()

        // call state update
        viewModel.updateField(CallState.INCALL)
        updateVideoCallStateFieldInDb()

        // add video call detail to realtime database
        CHANNEL_NAME = "Rufat"
        viewModel.addCallDetail(
            CallDetailResponse(
                id = CHANNEL_NAME,
                FirebaseAuth.getInstance().currentUser!!.uid,
                callReceiverId,
                callerName,
                callReceiverName,
                CallType.VIDEO,
                getCurrentTime(),
                null
            )
        )
        addCallDetailToDb()

        lifecycleScope.launch {
            setupVideSDKEngine()
        }

        // video call
        lifecycleScope.launchWhenStarted {

            joinChannel()

        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateVideoCallStateFieldInDb() {
        viewModel.updateVideoCallField.observe(viewLifecycleOwner) {

            when (it) {

                is NetworkResponse.LOADING -> {
                    Log.d(TAG, "updateVideoCallStateFieldInDb: State update is loading...")
                }

                is NetworkResponse.SUCCEED -> {
                    Log.d(
                        TAG,
                        "updateVideoCallStateFieldInDb: State update is successfully updated"
                    )
                }

                is NetworkResponse.ERROR -> {
                    Log.d(TAG, "updateVideoCallStateFieldInDb: State update error -> ${it.message}")
                }

            }

        }
    }

    private fun addCallDetailToDb() {
        viewModel.addCallDetail.observe(viewLifecycleOwner) {

            when (it) {

                is NetworkResponse.LOADING -> {
                    Log.d(TAG, "updateVideoCallStateFieldInDb: Call detail is adding...")
                }

                is NetworkResponse.SUCCEED -> {
                    Log.d(
                        TAG,
                        "updateVideoCallStateFieldInDb: Call detail is successfully added"
                    )
                }

                is NetworkResponse.ERROR -> {
                    Log.d(TAG, "updateVideoCallStateFieldInDb: Call detail error -> ${it.message}")
                }

            }

        }
    }

    private fun setupVideSDKEngine() {

        val RTCHandler = object : IRtcEngineEventHandler() {

            override fun onUserJoined(uid: Int, elapsed: Int) {
                super.onUserJoined(uid, elapsed)
                lifecycleScope.launchWhenStarted {
                    setupCallReceiverVideo(uid)
                }
            }

            override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                super.onJoinChannelSuccess(channel, uid, elapsed)
            }

            override fun onUserOffline(uid: Int, reason: Int) {
                super.onUserOffline(uid, reason)
                Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
            }
        }

        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = APP_ID
            config.mEventHandler = RTCHandler
            agoraEngine = RtcEngine.create(config)
            agoraEngine.enableVideo()
        } catch (e: Exception) {
            Log.d(TAG, "setupVideSDKEngine: Error is ${e.message}")
        }

    }

    private fun setupCallReceiverVideo(uid: Int) {

        remoteSurfaceView = SurfaceView(baseContext)
        remoteSurfaceView.setZOrderMediaOverlay(true)
        binding.receiverPersonVideo.addView(remoteSurfaceView)
        agoraEngine.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )

        remoteSurfaceView.visibility = VISIBLE
    }


    private fun setupCallerVideo() {

        localSurfaceView = SurfaceView(baseContext)
        binding.callerVideo.addView(localSurfaceView)
        agoraEngine.setupLocalVideo(VideoCanvas(localSurfaceView, VideoCanvas.RENDER_MODE_FIT, 0))

    }

    private fun joinChannel() {
        val options = ChannelMediaOptions()
        // For a Video call, set the channel profile as COMMUNICATION.
        options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
        // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
        // Display LocalSurfaceView.

        setupCallerVideo()
        localSurfaceView.visibility = VISIBLE
        // Start local preview.
        agoraEngine.startPreview()
        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        agoraEngine.joinChannel(TEMP_TOKEN, CHANNEL_NAME, 0, options)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        agoraEngine.stopPreview()
        agoraEngine.leaveChannel()
        _binding = null
    }

}