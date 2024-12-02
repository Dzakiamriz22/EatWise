package com.example.eatwise.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eatwise.R
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        val logo = findViewById<ImageView>(R.id.logo)
        val name = findViewById<TextView>(R.id.name)

        val fadeIn = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f)
        fadeIn.duration = 3000
        fadeIn.start()

        fadeIn.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                name.visibility = View.VISIBLE
                scope.launch {
                    displayTextOneByOne(name, "EatWise")
                    navigateToNextActivity()
                }
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    private suspend fun displayTextOneByOne(textView: TextView, fullText: String) {
        for (i in fullText.indices) {
            textView.text = fullText.substring(0, i + 1)
            delay(200)
        }
    }

    private fun navigateToNextActivity() {
        // Cek apakah onboarding telah selesai
        val sharedPreferences = getSharedPreferences("OnboardingPrefs", MODE_PRIVATE)
        val onboardingCompleted = sharedPreferences.getBoolean("OnboardingCompleted", false)

        val nextActivity = if (onboardingCompleted) {
            MainActivity::class.java // Navigasi ke MainActivity yang menampilkan HomeFragment
        } else {
            MainActivity::class.java // Navigasi ke Onboarding jika belum selesai
        }

        val intent = Intent(this, nextActivity)
        startActivity(intent)
        finish() // Mengakhiri SplashScreen
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() // Pastikan coroutine dihentikan saat activity dihancurkan
    }
}
