package com.codingwithrufat.bespeaker.di

import android.content.Context
import com.codingwithrufat.bespeaker.features.feature_auth.data.repository.CompletePorfileRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_auth.data.repository.RegisterRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.CompleteProfile
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
    fun provideRepo(auth: FirebaseAuth): RegisterRepository = RegisterRepository_Impl(auth)

    @Provides
    fun provideStorageReference() = FirebaseStorage.getInstance().reference.child("images/")

    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    fun provideCompleteRepo(
        storageReference: StorageReference,
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): CompleteProfile = CompletePorfileRepository_Impl(storageReference, auth.currentUser, db)


}