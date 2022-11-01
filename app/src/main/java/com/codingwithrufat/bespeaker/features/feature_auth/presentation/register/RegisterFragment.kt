package com.codingwithrufat.bespeaker.features.feature_auth.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.codingwithrufat.bespeaker.R
import com.codingwithrufat.bespeaker.common.utils.checkEmail
import com.codingwithrufat.bespeaker.common.utils.checkPassword
import com.codingwithrufat.bespeaker.common.utils.equal
import com.codingwithrufat.bespeaker.databinding.FragmentRegisterBinding
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RegisterFragment"

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private lateinit var viewModel: RegisterViewModel

    private val binding
        get() = _binding!!

    // vars
    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        observeUserRegisterState()
        clickedSignUpButton()
        clickedSignInButton()

        return binding.root
    }

    private fun clickedSignUpButton() {
        binding.buttonSignup.setOnClickListener {
            registerUser()
        }
    }

    private fun clickedSignInButton() {
        binding.txtSignIn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.loginFragment)
        }
    }

    private fun observeUserRegisterState() {
        viewModel.observeRegisterCase.observe(viewLifecycleOwner) { response ->

            when (response) {
                is NetworkResponse.LOADING -> Log.d(TAG, "onCreateView: User is registering now")

                is NetworkResponse.SUCCEED -> {
                    showSnackBarWhenMailSendToUserEmail(response.value.toString())
                    Log.d(
                        TAG,
                        "onCreateView: User successfully registered"
                    )
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    bundle.putString("password", password)
                    Navigation.findNavController(binding.root).navigate(R.id.completeProfileFragment, bundle)
                }

                is NetworkResponse.ERROR -> {
                    Log.d(TAG, "onCreateView: Error is ${response.error_msg}")
                }
            }

        }
    }

    private fun registerUser() {

        email = binding.editEmail.text?.trim().toString()
        password = binding.editPassword.text?.trim().toString()

        if (binding.editEmail.checkEmail()
            && binding.editPassword.checkPassword()
            && binding.editConfirmPassword.checkPassword()
            && binding.editPassword equal binding.editConfirmPassword
        ){
            val userRegister = UserRegister.Builder()
                .email(email)
                .password(password)
                .completeStatus(false)
                .build()
            viewModel.register(userRegister)
        }

    }

    private fun showSnackBarWhenMailSendToUserEmail(email: String) {

        Snackbar.make(binding.relLayout, "Verification email sent to $email", Snackbar.LENGTH_LONG).show()

    }

}