package com.example.pharmaecomapp.activity

import android.content.Intent
import android.graphics.Color
import com.example.pharmaecomapp.databinding.ActivityLoginBinding


import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    // Using View Binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Change the status bar color to white
        window.statusBarColor = Color.WHITE

        // Set the status bar text and icon color to black
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        login()
        skipLogin()







    }

    private fun skipLogin() {
        // Set onClickListener for Forgot Password TextView
        binding.tvSkipLogin.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Skipped Login..", Toast.LENGTH_SHORT).show()
            // Navigate to the Forgot Password screen or perform any further actions
        }
    }

    private fun login() {
        // Set onClickListener for Login Button
        binding.btnLogin.setOnClickListener {
            // Get input values from EditTexts
            val mobileNumber = binding.editMobNo.text.toString()
            val password = binding.editPassword.text.toString()

            // Validate input
            if (mobileNumber.isEmpty() || password.isEmpty()) {
                // If any field is empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (mobileNumber == "9696969696" && password == "123456") {
                // If login credentials are correct


                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()// Launch the new activity
                // Navigate to the next screen or perform any further actions
            } else {
                // If login credentials are incorrect
                Toast.makeText(this, "Incorrect mobile number or password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
