package com.example.eatwise.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.ui.custom_dialog.CustomDialogFragment
import com.example.eatwise.R
import com.example.eatwise.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity(R.layout.activity_edit) {

    private val binding by viewBinding(ActivityEditBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupButton()
    }

    private fun setupButton() {
        binding.actionImage.setOnClickListener {
            finish()
        }
        binding.username.setOnClickListener {
            val dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager, "username_dialog")
        }
        binding.gender.setOnClickListener {
            val dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager, "gender_dialog")
        }
        binding.birthday.setOnClickListener {
            val dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager, "birthday_dialog")
        }
        binding.goal.setOnClickListener {
            val dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager, "goal_dialog")
        }
    }
}