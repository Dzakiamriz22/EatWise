package com.example.eatwise.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eatwise.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private var binding: ActivitySignupBinding? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Navigate to SigninActivity when "Sign in" text is clicked
        binding?.tvSignin?.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }

        // Handle signup button click
        binding?.tvSignup?.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val username = binding?.edUsername?.text.toString()
        val email = binding?.edEmail?.text.toString()
        val password = binding?.edPassword?.text?.toString()

        if (validateForm(username, email, password)) {
            auth.createUserWithEmailAndPassword(email, password!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // User created successfully
                        Toast.makeText(this, "User account created successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        // User creation failed
                        Toast.makeText(this, "User account not created. Try again later", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateForm(username: String, email: String, password: String?): Boolean {
        return when {
            TextUtils.isEmpty(username) -> {
                binding?.layoutUsername?.error = "Enter username"
                false
            }
            TextUtils.isEmpty(email) -> {
                binding?.layoutEmailInput?.error = "Enter valid email address"
                false
            }
            TextUtils.isEmpty(password) -> {
                binding?.layoutPasswordInput?.error = "Enter password"
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
