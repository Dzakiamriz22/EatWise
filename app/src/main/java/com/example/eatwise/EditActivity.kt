package com.example.eatwise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity(R.layout.activity_edit) {
    private val binding by viewBinding(ActivityEditBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupButton()
    }

    private fun setupButton(){
        binding.actionImage.setOnClickListener {
            finish()
        }
    }
}