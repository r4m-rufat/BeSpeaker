package com.codingwithrufat.bespeaker.features.feature_home.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithrufat.bespeaker.common.listeners.OnClickedAcceptOrRefuseListener
import com.codingwithrufat.bespeaker.common.listeners.OnClickedCallButtonListener
import com.codingwithrufat.bespeaker.common.services.ServiceManager
import com.codingwithrufat.bespeaker.common.utils.*
import com.codingwithrufat.bespeaker.databinding.FragmentHomeBinding
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.CallNotification
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(), OnClickedCallButtonListener, OnClickedAcceptOrRefuseListener {

    private var _binding: FragmentHomeBinding? = null

    private val binding
        get() = _binding as FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var usersAdapter: UserItemAdapter
    private lateinit var notificationsAdapter: CallNotificationItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        setupAdapters()
        viewModel.getActiveUsers(UserActivity.ONLINE("online"))
        ServiceManager.updateLastSeenWithWorker(requireActivity(), UserActivity.ONLINE("online"))
        observeActiveUsers()

        // get notifications and observe them asynchronously
        viewModel.getObservableNotifications()
        observeNotifications()

        lifecycleScope.launch {
            lifecycleScope.launch {
                writeUserInformationsToDataStore(
                    requireContext(),
                    FirebaseAuth.getInstance().currentUser!!.uid,
                    "Rufat",
                    EnglishLevel.B2,
                    "https://firebasestorage.googleapis.com/v0/b/bespeaker-62103.appspot.com/o/images%2FwsjLFmKT25hwIEIpYvG6eTDmN5m2%2F1666631882410_239479e8-8831-4ae8-b8b5-c786d21db06b.png?alt=media&token=e0e74cd0-ca98-4cb3-8c1c-1467a168decd"
                )
            }
        }

        return binding.root
    }

    private fun setupAdapters() {

        usersAdapter = UserItemAdapter(requireContext(), emptyList(), this, lifecycleScope)

        binding.recyclerHome.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        notificationsAdapter = CallNotificationItemAdapter(requireContext(), emptyList(), this)

        binding.recyclerNotifications.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = notificationsAdapter
        }

    }

    private fun observeNotifications() {

        viewModel.notification.observe(viewLifecycleOwner) { response ->

            when(response) {

                is NetworkResponse.SUCCEED -> {
                    Log.d(TAG, "observeNotifications: Notifications are successfully been observing")
                    response.data?.let {
                        notificationsAdapter.updateList(it)
                    }
                }

                is NetworkResponse.LOADING -> {
                    Log.d(TAG, "observeNotifications: Notifications are loading")
                }

                is NetworkResponse.ERROR -> {
                    Log.d(TAG, "observeNotifications: Error is ${response.message}")
                }

            }

        }

    }

    private fun observeActiveUsers() {

        viewModel.active_users.observe(viewLifecycleOwner) { response ->

            when (response) {

                is NetworkResponse.SUCCEED -> {
                    Log.d(TAG, "observeActiveUsers: Data is successfully loaded")
                    response.data?.let {
                        usersAdapter.updateList(it)
                    }
                }

                is NetworkResponse.LOADING -> {
                    Log.d(TAG, "observeActiveUsers: Data is loading")
                }

                is NetworkResponse.ERROR -> {
                    Log.d(TAG, "observeActiveUsers: Error is ${response.message}")
                }

            }

        }

    }



    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onDestroy: Service is running")
        ServiceManager.updateLastSeenWithWorker(requireActivity(), UserActivity.OFFLINE(getCurrentTime()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickButton(callUserID: String, callModel: CallNotification) {

        viewModel.callUserAndAddCallModelToDB(callUserID, callModel)
    }

    override fun onClickedButton(senderID: String) {
        viewModel.removeNotification(senderID)
    }

}