package com.codingwithrufat.bespeaker.di

import com.codingwithrufat.bespeaker.features.feature_auth.presentation.AuthenticationActivity
import dagger.Component

@Component
interface ApplicationComponent {
    fun inject(authenticationActivity: AuthenticationActivity)
}