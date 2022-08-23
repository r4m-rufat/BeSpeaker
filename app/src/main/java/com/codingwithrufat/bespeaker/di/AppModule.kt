package com.codingwithrufat.bespeaker.di

import com.codingwithrufat.bespeaker.features.feature_auth.data.repository.RegisterRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideUser() = FirebaseAuth.getInstance().currentUser

    @Provides
    fun provideRepo(auth: FirebaseAuth): RegisterRepository = RegisterRepository_Impl(auth)


}