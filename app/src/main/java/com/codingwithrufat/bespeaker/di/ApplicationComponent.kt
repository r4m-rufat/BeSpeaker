package com.codingwithrufat.bespeaker.di

import com.codingwithrufat.bespeaker.presentation.MainActivity
import dagger.Component

@Component
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}