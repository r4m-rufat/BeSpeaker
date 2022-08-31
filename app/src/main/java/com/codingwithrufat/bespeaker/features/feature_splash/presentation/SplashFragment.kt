package com.codingwithrufat.bespeaker.features.feature_splash.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.codingwithrufat.bespeaker.R
import com.codingwithrufat.bespeaker.databinding.FragmentSplashBinding
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckEmailResponse
import com.codingwithrufat.bespeaker.features.feature_splash.domain.util.CheckProfileStatusResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SplashFragment"

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    private val viewModel by viewModels<SplashViewModel>()

    private val binding
        get() = _binding as FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(layoutInflater)


        checkUserCasesForNavigation()

        CoroutineScope(IO).launch {

            delay(2000L)
            viewModel.checkEmailVerifiedOrNot()

        }
        return binding.root
    }

    private fun checkUserCasesForNavigation() {

        viewModel.emailVerified.observe(viewLifecycleOwner) { emailResponse ->

            when (emailResponse) {

                is CheckEmailResponse.EmailIsVerified -> {

                    Log.d(TAG, "checkUserCasesForNavigation: Email Response is \"EmailIsVerified\"")
                    /** another condition is user status
                     *  if "complete_profile_status" is true then profile_status return true
                     *  otherwise it is false that means profile is not completed yet
                     */
                    viewModel.checkUserProfileStatus()
                    checkProfileStatusToNavigateHomeScreen()


                }

                is CheckEmailResponse.EmailIsNotVerified -> {

                    Log.d(
                        TAG,
                        "checkUserCasesForNavigation: Email Response is \"EmailIsNotVerified\""
                    )
                    // navigate to Register Fragment
                    Navigation.findNavController(binding.root).navigate(R.id.registerFragment)

                }

                is CheckEmailResponse.NoAnyUser -> {

                    Log.d(TAG, "checkUserCasesForNavigation: Email Response is \"NoAnyUser\"")
                    // navigate to Register Fragment
                    Navigation.findNavController(binding.root).navigate(R.id.registerFragment)

                }

            }

        }

    }

    private fun checkProfileStatusToNavigateHomeScreen() {

        viewModel.profileStatus.observe(viewLifecycleOwner) { profile_status ->

            when (profile_status) {

                is CheckProfileStatusResponse.ProfileIsCompleted -> {

                    Log.d(
                        TAG,
                        "checkProfileStatusToNavigateHomeScreen: Profile Status is \"ProfileIsCompleted\""
                    )
                    // navigate to Home Fragment
                    Navigation.findNavController(binding.root).navigate(R.id.homeFragment)

                }

                is CheckProfileStatusResponse.ProfileIsNotCompleted -> {

                    Log.d(
                        TAG,
                        "checkProfileStatusToNavigateHomeScreen: Profile Status is \"ProfileIsNotCompleted\""
                    )
                    // navigate to Complete Profile Fragment
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.completeProfileFragment)

                }

                is CheckProfileStatusResponse.ERROR -> {

                    Log.d(
                        TAG,
                        "checkProfileStatusToNavigateHomeScreen: Profile Status is \"ERROR\"\n Exception > ${profile_status.err_msg}"
                    )

                    // navigate to Login Fragment
                    Navigation.findNavController(binding.root).navigate(R.id.loginFragment)

                }

            }

        }

    }

}