package com.example.eatwise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.databinding.ActivityAboutmeBinding

class AboutmeActivity : AppCompatActivity(R.layout.activity_aboutme) {
    private val binding by viewBinding(ActivityAboutmeBinding::bind)
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