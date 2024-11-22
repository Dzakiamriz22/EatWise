package com.example.eatwise.activity

import android.content.Intent
import android.graphics.drawable.DrawableContainer
import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.eatwise.R
import com.example.eatwise.adapter.OnboardingItemAdapter
import com.example.eatwise.data.OnboardingItem
import com.example.eatwise.ui.home.HomeFragment

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingItemAdapter: OnboardingItemAdapter
    private lateinit var indicatorsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isFirstTimeLaunching()) {
            setContentView(R.layout.activity_onboarding)
            initializeOnboarding()
        } else {
            navigateToMain()
        }
    }

    private fun isFirstTimeLaunching(): Boolean {
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        return sharedPreferences.getBoolean("onboarding_shown", true)
    }

    private fun setOnboardingShown() {
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("onboarding_shown", false)
            apply()
        }
    }

    private fun initializeOnboarding() {
        val onboardingViewPager = findViewById<ViewPager2>(R.id.vp_onboarding)

        onboardingItemAdapter = OnboardingItemAdapter(
            listOf(
                OnboardingItem(
                    ivImage = R.drawable.hero,
                    tvTitle = "Selamat Datang di EatWise",
                    tvDesc = "Bersama EatWise, langkah pertama menuju hidup sehat lebih mudah! Mulailah perjalanan kesehatan Anda dengan alat cerdas untuk memahami kebutuhan nutrisi Anda."
                ),
                OnboardingItem(
                    ivImage = R.drawable.hero,
                    tvTitle = "Analisis Nutrisi Real-Time",
                    tvDesc = "Gunakan teknologi AI canggih kami untuk memindai makanan Anda dan dapatkan informasi kalori serta nutrisi secara instan!"
                ),
                OnboardingItem(
                    ivImage = R.drawable.hero,
                    tvTitle = "Rekomendasi Gizi Personal",
                    tvDesc = "Dapatkan saran nutrisi yang disesuaikan dengan kebutuhan dan tujuan kesehatan Anda, dari menurunkan berat badan hingga meningkatkan vitalitas."
                )
            )
        )

        onboardingViewPager.adapter = onboardingItemAdapter
        setupIndicators()

        onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < onboardingItemAdapter.itemCount) {
                onboardingViewPager.currentItem += 1
            } else {
                navigateToMain()
            }
        }

        findViewById<Button>(R.id.btnSkip).setOnClickListener {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        setOnboardingShown()
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById(R.id.indicator)
        val indicators = arrayOfNulls<ImageView>(onboardingItemAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(9, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}