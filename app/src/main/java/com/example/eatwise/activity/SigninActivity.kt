package com.example.eatwise.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.eatwise.R
import com.example.eatwise.data.User
import com.example.eatwise.data.repository.UserRepository
import com.example.eatwise.databinding.ActivitySigninBinding
import com.example.eatwise.network.ApiClient
import com.example.eatwise.ui.profile.ProfileViewModel
import com.example.eatwise.viewmodel.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var sharedPreferences: SharedPreferences
    private val profileViewModel by lazy {
        val factory = UserViewModelFactory(UserRepository(ApiClient.apiService))
        ViewModelProvider(this,factory)[ProfileViewModel::class.java]
    }

    // Launcher for Google Sign-In
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleGoogleSignInResult(task)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("Eatwise", MODE_PRIVATE)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check if user is already logged in
        if (auth.currentUser != null) {
            navigateToMainActivity()
        }

        // Set up click listeners
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.tvSignin.setOnClickListener {
            loginUser()
        }

        binding.signinGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun loginUser() {
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()

        if (validateForm(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val editor = sharedPreferences.edit()
                        editor.putString("uid", auth.currentUser?.uid)
                        editor.putString("name", "Default User")
                        editor.putString("email", auth.currentUser?.email)
                        editor.apply()
                        profileViewModel.updateUserProfile(
                            User(
                                auth.currentUser?.uid!!,
                                "Default User",
                                0,
                                "Male",
                                0f,
                                0f,
                                ""
                            )
                        )
                        navigateToMainActivity()
                    } else {
                        showToast(task.exception?.message ?: "Login failed. Please try again.")
                    }
                }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.result
            account?.let {
                firebaseAuthWithGoogle(it)
            }
        } catch (e: Exception) {
            showToast("Google Sign-In failed. Please try again.")
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val editor = sharedPreferences.edit()
                editor.putString("uid", auth.currentUser?.uid)
                editor.putString("name", auth.currentUser?.displayName)
                editor.putString("email", auth.currentUser?.email)
                editor.apply()
                profileViewModel.updateUserProfile(
                    User(
                        auth.currentUser?.uid!!,
                        auth.currentUser?.displayName!!,
                        0,
                        "Male",
                        0f,
                        0f,
                        ""
                    )
                )
                navigateToMainActivity()
            } else {
                showToast(task.exception?.message ?: "Authentication failed. Please try again.")
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(email)) {
            binding.layoutEmailInput.error = "Enter a valid email address"
            isValid = false
        } else {
            binding.layoutEmailInput.error = null
        }

        if (TextUtils.isEmpty(password)) {
            binding.layoutPasswordInput.error = "Enter your password"
            isValid = false
        } else {
            binding.layoutPasswordInput.error = null
        }

        return isValid
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // ViewBinding tidak perlu di-set null di Kotlin modern
    }
}
