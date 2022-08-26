package com.codingwithrufat.bespeaker.features.feature_auth.presentation.ui.complete_profile

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codingwithrufat.bespeaker.common.DatePickerDialog
import com.codingwithrufat.bespeaker.common.IMAGE_RESULT_OK
import com.codingwithrufat.bespeaker.databinding.FragmentCompleteProfileBinding
import com.codingwithrufat.bespeaker.features.feature_auth.domain.model.UserRegister
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.EnglishLevel
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.GenderType
import com.codingwithrufat.bespeaker.features.feature_auth.domain.util.NetworkResponse
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


            observeCompleteProfileAndLoadingImageProgress()

        }

    }

    private fun getUserInputsAndExecuteViewModelActions(imageLink: String) {

        val userRegister = UserRegister.Builder()
            .name(binding.editName.text.trim().toString())
            .surname(binding.editSurname.text.trim().toString())
            .englishLevel(EnglishLevel.values()[binding.englishLevelSpinner.selectedItemPosition])
            .birthday("${binding.txtDay.text}-${binding.txtMonth.text}-${binding.txtDay.text}")
            .gender(GenderType.values()[binding.genderRadioGroup.checkedRadioButtonId])
            .profileImageLink(imageLink)
            .build()

        imageUri?.let {

            viewModel.loadImageToDB(it)

        }
        viewModel.completeProfile(userRegister)

    }

    private fun observeCompleteProfileAndLoadingImageProgress() {

        /**
         * @param response holds two different network call in one network response sequentially
         * first -> (LoadingImage progress) returns int until(100%)
         * after first progress is finished next action is started automatically in background thread
         * second -> (CompleteProfile response) returns state
         */
        viewModel.imageAndCompleteProfileState.observe(viewLifecycleOwner) { response ->

            when(response) {

                is NetworkResponse.SUCCEED ->
                    if (response.value is String) { // this means image link came
                        getUserInputsAndExecuteViewModelActions(response.value)
                    }else { // if it is null then all of actions is completed
                        // TODO to navigate Home Fragment
                    }

                is NetworkResponse.LOADING -> {

                    if (response.percent is Int){ // it means file is uploading now and it returns percentage
                        // TODO showProgress(percentage)
                    }else {
                        // TODO showProgress(string)
                    }

                }

                is NetworkResponse.ERROR -> {
                    // TODO hideProgress
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