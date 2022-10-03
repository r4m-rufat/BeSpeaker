package com.codingwithrufat.bespeaker.di

import com.codingwithrufat.bespeaker.features.feature_auth.data.repository.CompletePorfileRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_auth.data.repository.LoginRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_auth.data.repository.RegisterRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.CompleteProfile
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.LoginRepository
import com.codingwithrufat.bespeaker.features.feature_auth.domain.repository.RegisterRepository
import com.codingwithrufat.bespeaker.features.feature_home.data.repository.HomePageRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_home.domain.repository.HomePageRepository
import com.codingwithrufat.bespeaker.features.feature_splash.data.repository.CheckUserRepository_Impl
import com.codingwithrufat.bespeaker.features.feature_splash.domain.repository.CheckUser
import com.google.firebase.auth.FirebaseAuth
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
    fun provideRegisterRepo(auth: FirebaseAuth, db: FirebaseFirestore): RegisterRepository = RegisterRepository_Impl(auth, db)

    @Provides
    fun provideStorageReference() = FirebaseStorage.getInstance().reference.child("images/")

    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    fun provideCompleteRepo(
        storageReference: StorageReference,
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): CompleteProfile = CompletePorfileRepository_Impl(storageReference, auth, db)

    @Provides
    fun provideLoginRepo(auth: FirebaseAuth, db: FirebaseFirestore): LoginRepository = LoginRepository_Impl(auth, db)

    @Provides
    fun provideCheckUserRepo(auth: FirebaseAuth, db: FirebaseFirestore): CheckUser = CheckUserRepository_Impl(auth, db)

    @Provides
    fun provideHomePageRepo(auth: FirebaseAuth, db: FirebaseFirestore): HomePageRepository = HomePageRepository_Impl(auth, db)

}