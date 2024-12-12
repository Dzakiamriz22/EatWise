package com.example.eatwise.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.eatwise.R
import com.example.eatwise.adapter.OnboardingItemAdapter
import com.example.eatwise.data.OnboardingItem

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingItemAdapter: OnboardingItemAdapter
    private lateinit var indicatorsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFirstTimeLaunching()) {
            setContentView(R.layout.activity_onboarding)
            initializeOnboarding()
        } else {
            navigateToSignIn()
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
                    tvTitle = "Welcome to EatWise",
                    tvDesc = "With EatWise, taking the first step towards a healthy lifestyle is easier! Start your health journey with smart tools to understand your nutritional needs."
                ),
                OnboardingItem(
                    ivImage = R.drawable.hero,
                    tvTitle = "Real-Time Nutrition Analysis",
                    tvDesc = "Use our advanced AI technology to scan your meals and instantly receive calorie and nutritional information!"
                ),
                OnboardingItem(
                    ivImage = R.drawable.hero,
                    tvTitle = "Personalized Nutrition Recommendations",
                    tvDesc = "Get nutrition advice tailored to your needs and health goals, from weight loss to enhanced vitality."
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
                navigateToSignIn()
            }
        }

        findViewById<Button>(R.id.btnSkip).setOnClickListener {
            navigateToSignIn()
        }
    }

    private fun navigateToSignIn() {
        setOnboardingShown()
        startActivity(Intent(this, SigninActivity::class.java))
        finish()
    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById(R.id.indicator)
        val indicators = arrayOfNulls<ImageView>(onboardingItemAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(9, 0, 8, 0) }

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this.layoutParams = layoutParams
                indicatorsContainer.addView(this)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    if (i == position) R.drawable.indicator_active else R.drawable.indicator_inactive
                )
            )
        }
    }
}