package com.codingwithrufat.bespeaker.features.feature_auth.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.codingwithrufat.bespeaker.databinding.FragmentLoginBinding
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserLogin
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding
    get() = _binding as FragmentLoginBinding

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(layoutInflater)

        clickedSignInButton()

        return binding.root
    }

    private fun clickedSignInButton() {

        binding.buttonSignin.setOnClickListener {

            val user = UserLogin(
                binding.editEmail.text?.trim().toString(),
                binding.editPassword.text?.trim().toString(),
            )
            viewModel.loginUser(user)
            observeNetworkResponse()

        }

    }

    private fun observeNetworkResponse() {

        viewModel.loginResponse.observe(viewLifecycleOwner) { response ->

            when(response) {

                is NetworkResponse.LOADING ->
                    Log.d(TAG, "observeNetworkResponse: Checking user's profile information")

                is NetworkResponse.SUCCEED -> {

                    Log.d(TAG, "observeNetworkResponse: User successfully login")
                    // TODO navigate to Complete Profile screen if profile status is false

                }

                is NetworkResponse.ERROR ->
                    Log.d(TAG, "observeNetworkResponse: Error happened -> \n ${response.error_msg}")

            }

        }

    }

}