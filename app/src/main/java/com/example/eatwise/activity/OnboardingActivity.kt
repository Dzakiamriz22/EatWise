package com.example.eatwise.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.eatwise.R
import com.example.eatwise.adapter.OnboardingItemAdapter
import com.example.eatwise.data.OnboardingItem

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingItemAdapter: OnboardingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        setupOnboardingItems()
    }

    private fun setupOnboardingItems() {
        onboardingItemAdapter = OnboardingItemAdapter(
            listOf(
                OnboardingItem(
                    ivImage = R.drawable.hero,
                    tvTitle = "Selamat Datang di EatWise",
                    tvDesc = "Bersama EatWise, langkah pertama menuju hidup sehat lebih mudah! Mulailah perjalanan kesehatan Anda dengan alat cerdas untuk memahami kebutuhan nutrisi Anda."
                ),
                OnboardingItem(
                    ivImage = R.drawable.hero,
                    tvTitle = "Selamat Datang di EatWise",
                    tvDesc = "Bersama EatWise, langkah pertama menuju hidup sehat lebih mudah! Mulailah perjalanan kesehatan Anda dengan alat cerdas untuk memahami kebutuhan nutrisi Anda."
                ),
                OnboardingItem(
                    ivImage = R.drawable.hero,
                    tvTitle = "Selamat Datang di EatWise",
                    tvDesc = "Bersama EatWise, langkah pertama menuju hidup sehat lebih mudah! Mulailah perjalanan kesehatan Anda dengan alat cerdas untuk memahami kebutuhan nutrisi Anda."
                )
            )
        )
        val onboardingViewPager2 = findViewById<ViewPager2>(R.id.vp_onboarding)
        onboardingViewPager2.adapter = onboardingItemAdapter
    }
}