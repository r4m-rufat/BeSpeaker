package com.codingwithrufat.bespeaker.features.feature_auth.presentation.ui.complete_profile

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.codingwithrufat.bespeaker.common.DatePickerDialog
import com.codingwithrufat.bespeaker.databinding.FragmentCompleteProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompleteProfileFragment : Fragment() {

    private var _binding: FragmentCompleteProfileBinding? = null

    private val binding
    get() = _binding as FragmentCompleteProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompleteProfileBinding.inflate(layoutInflater)

        binding.btnSetDate.setOnClickListener {

            DatePickerDialog.showAndListenDatePicker(
                requireActivity(),
                triple = { triple ->
                    binding.txtDay.text = triple.third.toString()
                    binding.txtMonth.text = triple.second.toString()
                    binding.txtYear.text = triple.first.toString()
                }
            )

        }

        return binding.root

    }



}