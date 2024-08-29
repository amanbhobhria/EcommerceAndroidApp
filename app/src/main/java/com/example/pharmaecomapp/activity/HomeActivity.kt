package com.example.pharmaecomapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pharmaecomapp.R
import com.example.pharmaecomapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}