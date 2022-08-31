package com.codingwithrufat.bespeaker.di

import com.codingwithrufat.bespeaker.features.MainActivity
import dagger.Component

@Component
interface ApplicationComponent {
    fun inject(authenticationActivity: MainActivity)
}