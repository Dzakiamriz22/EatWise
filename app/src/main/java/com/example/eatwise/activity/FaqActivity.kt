package com.example.eatwise.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.R
import com.example.eatwise.databinding.ActivityFaqBinding

class FaqActivity : AppCompatActivity(R.layout.activity_faq) {
    private val binding by viewBinding(ActivityFaqBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupButton()
    }

    private fun setupButton() {
        binding.actionImage.setOnClickListener {
            finish()
        }
    }
}