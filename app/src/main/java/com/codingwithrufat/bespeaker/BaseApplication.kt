package com.codingwithrufat.bespeaker

import android.app.Application
import com.codingwithrufat.bespeaker.di.ApplicationComponent
import com.codingwithrufat.bespeaker.di.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application()