package com.codingwithrufat.bespeaker.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
/**
 * Hilt doesn't accept BaseFragment with generics or constructor elements
 * It is annoying operation for this reason and is not used for now
 */
abstract class BaseFragment<VM: ViewModel, VB: ViewBinding>: Fragment() {

    protected var _binding: VB? = null
    protected lateinit var viewModel: VM

    protected val binding: VB
    get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = getViewBinding()

        if (_binding == null)
            throw IllegalArgumentException("Binding is null, but it can't")

        viewModel = ViewModelProvider(this)[getViewModel()]

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getViewBinding(): VB

}