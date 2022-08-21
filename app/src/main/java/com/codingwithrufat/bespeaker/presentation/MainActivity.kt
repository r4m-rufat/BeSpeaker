package com.codingwithrufat.bespeaker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codingwithrufat.bespeaker.R
import com.codingwithrufat.bespeaker.databinding.ActivityMainBinding
import com.codingwithrufat.bespeaker.presentation.ui.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}