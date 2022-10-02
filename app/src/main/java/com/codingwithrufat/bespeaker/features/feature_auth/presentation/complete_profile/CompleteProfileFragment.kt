package com.codingwithrufat.bespeaker.features.feature_auth.presentation.complete_profile

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.codingwithrufat.bespeaker.R
import com.codingwithrufat.bespeaker.common.DatePickerDialog
import com.codingwithrufat.bespeaker.common.IMAGE_RESULT_OK
import com.codingwithrufat.bespeaker.common.hideHorizontalProgress
import com.codingwithrufat.bespeaker.common.showHorizontalProgress
import com.codingwithrufat.bespeaker.databinding.FragmentCompleteProfileBinding
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.EnglishLevel
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.GenderType
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*

private const val TAG = "CompleteProfileFragment"

@AndroidEntryPoint
class CompleteProfileFragment : Fragment() {

    private var _binding: FragmentCompleteProfileBinding? = null

    private val binding
        get() = _binding as FragmentCompleteProfileBinding

    // vars
    private var imageUri: Uri? = null
    private lateinit var viewModel: CompleteProfileViewModel
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString("email")!!
            password = it.getString("password")!!
            Log.d(TAG, "onCreate: Email -> $email and Password -> $password")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCompleteProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CompleteProfileViewModel::class.java]

        clickedSelectFloatingActionProfileButton()
        setDateButton()
        clickedCompleteButton()

        return binding.root

    }

    private fun clickedCompleteButton() {

        binding.completeProfileButton.setOnClickListener {

            imageUri?.let {
                Log.d(TAG, "clickedCompleteButton: Image uri is not empty")
                viewModel.loadImageToDB(it)

            }
            observeCompleteProfileAndLoadingImageProgress()

        }

    }

    private fun getUserInputsAndExecuteViewModelActions(imageLink: String) {

        val userRegister = UserRegister.Builder()
            .id(FirebaseAuth.getInstance().currentUser!!.uid)
            .email(email)
            .password(password)
            .name(binding.editName.text.trim().toString())
            .surname(binding.editSurname.text.trim().toString())
            .englishLevel(EnglishLevel.values()[binding.englishLevelSpinner.selectedItemPosition].string)
            .birthday("${binding.txtDay.text}-${binding.txtMonth.text}-${binding.txtYear.text}")
            .gender(GenderType.values()[getSelectedRadioButtonIndex()].name)
            .profileImageLink(imageLink)
            .completeStatus(true)
            .build()

        viewModel.completeProfile(userRegister)

    }

    private fun getSelectedRadioButtonIndex(): Int {
        val radioButtonId = binding.genderRadioGroup.checkedRadioButtonId
        val radioButton = binding.genderRadioGroup.findViewById<RadioButton>(radioButtonId)
        return binding.genderRadioGroup.indexOfChild(radioButton)
    }

    private fun observeCompleteProfileAndLoadingImageProgress() {

        /**
         * @param response holds two different network call in one network response sequentially
         * first -> (LoadingImage progress) returns int until(100%)
         * after first progress is finished next action is started automatically in background thread
         * second -> (CompleteProfile response) returns state
         */
        viewModel.imageAndCompleteProfileState.observe(viewLifecycleOwner) { response ->

            when (response) {

                is NetworkResponse.SUCCEED ->
                    if (response.value is String) { // this means image link came
                        Log.d(
                            TAG,
                            "observeCompleteProfileAndLoadingImageProgress: Link is ${response.value}}"
                        )
                        getUserInputsAndExecuteViewModelActions(response.value)
                    } else { // if it is null then all of actions is completed
                        // TODO to navigate Home Fragment
                        binding.progressLayout.hideHorizontalProgress()
                        Log.d(
                            TAG,
                            "observeCompleteProfileAndLoadingImageProgress: We can navigate to home "
                        )
                        Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
                    }

                is NetworkResponse.LOADING -> {

                    if (response.percent != null){
                        // file is uploading now and it returns percentage
                        Log.d(
                            TAG,
                            "observeCompleteProfileAndLoadingImageProgress: Loading...(${response.percent})"
                        )
                        binding.progressLayout.showHorizontalProgress(binding.progressHorizontal, binding.txtTitle, binding.txtPercent, response.percent, "Uploading your photo...")
                    }else {
                        binding.progressLayout.showHorizontalProgress(binding.txtTitle, binding.txtPercent, "Completing your profile...")
                        Log.d(
                            TAG,
                            "observeCompleteProfileAndLoadingImageProgress: Everything loaded"
                        )

                    }


                }

                is NetworkResponse.ERROR -> {
                    // TODO hideProgress
                    binding.progressLayout.hideHorizontalProgress()
                    Log.d(
                        TAG,
                        "observeCompleteProfileAndLoadingImageProgress: Error is -> \n ${response.error_msg.message}"
                    )
                }

            }

        }

    }

    private fun clickedSelectFloatingActionProfileButton() {
        binding.imgSelectProfileButton.setOnClickListener {
            getImagePickerAndSetProfileImage()
        }
    }


    private fun setDateButton() {
        binding.btnSetDate.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                DatePickerDialog.showAndListenDatePicker(
                    requireActivity(),
                    triple = { triple -> // Triple(year, month, day)
                        binding.txtDay.text = triple.third // day
                        binding.txtMonth.text = triple.second // month
                        binding.txtYear.text = triple.first // year
                    }
                )
            }

        }
    }

    private fun getImagePickerAndSetProfileImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_RESULT_OK)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_RESULT_OK) {
            try {
                imageUri = data?.data
                val imageStream: InputStream? = imageUri?.let {
                    requireContext().contentResolver.openInputStream(
                        it
                    )
                }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                binding.imgProfile.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Log.d(TAG, "onActivityResult: Something went wrong")
            }
        } else {
            Log.d(TAG, "onActivityResult: You haven't picked Image")
        }

    }

}