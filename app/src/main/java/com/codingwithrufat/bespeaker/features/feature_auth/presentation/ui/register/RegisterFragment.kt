package com.codingwithrufat.bespeaker.features.feature_auth.presentation.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codingwithrufat.bespeaker.common.checkEmail
import com.codingwithrufat.bespeaker.common.checkPassword
import com.codingwithrufat.bespeaker.common.equal
import com.codingwithrufat.bespeaker.databinding.FragmentRegisterBinding
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RegisterFragment"

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private lateinit var viewModel: RegisterViewModel

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        observeUserRegisterState()
        clickedSignUpButton()

        return binding.root
    }

    private fun clickedSignUpButton() {
        binding.buttonSignup.setOnClickListener {
            registerUser()

        }
    }

    private fun observeUserRegisterState() {
        viewModel.observeRegisterCase.observe(viewLifecycleOwner) { response ->

            when (response) {
                is NetworkResponse.LOADING -> Log.d(TAG, "onCreateView: User is registering now")

                is NetworkResponse.SUCCEED -> Log.d(
                    TAG,
                    "onCreateView: User successfully registered"
                )

                is NetworkResponse.ERROR -> {
                    Log.d(TAG, "onCreateView: Error is ${response.error_msg}")
                }
            }

        }
    }

    private fun registerUser() {

        if (binding.editEmail.checkEmail()
            && binding.editPassword.checkPassword()
            && binding.editConfirmPassword.checkPassword()
            && binding.editPassword equal binding.editConfirmPassword
        ){
            val userRegister = UserRegister.Builder()
                .email(binding.editEmail.text?.trim().toString())
                .password(binding.editPassword.text?.trim().toString())
                .build()
            viewModel.register(userRegister)
        }

    }

}