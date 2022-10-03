package com.codingwithrufat.bespeaker.features.feature_home.presentation

import androidx.lifecycle.ViewModel
import com.codingwithrufat.bespeaker.features.feature_home.domain.use_case.HomePageUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val useCase: HomePageUseCase
): ViewModel() {



}