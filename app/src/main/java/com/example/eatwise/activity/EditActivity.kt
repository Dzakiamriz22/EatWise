package com.example.eatwise.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.R
import com.example.eatwise.databinding.ActivityEditBinding
import java.util.*

class EditActivity : AppCompatActivity(R.layout.activity_edit) {

    private val binding by viewBinding(ActivityEditBinding::bind)
    private var selectedGoal: String? = null
    private var selectedBirthday: String? = null
    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Birthday, Goal, and Gender interactions
        setupBirthdayPicker()
        setupGoalDropdown()
        setupGenderDropdown()

        // Handle Save button click
        binding.btnSave.setOnClickListener {
            if (isInputValid()) {
                saveData()
            } else {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupBirthdayPicker() {
        // Handle DatePickerDialog for selecting birthday
        binding.resultBirthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedBirthday = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.resultBirthday.text = selectedBirthday
            }, year, month, day).apply {
                // Customize DatePickerDialog style to match the app theme
                setTitle("Select Birthday")
            }.show()
        }
    }

    private fun setupGoalDropdown() {
        // Customize PopupMenu for selecting goal
        binding.resultGoal.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.resultGoal)
            val goals = listOf("Lose weight", "Maintain weight", "Gain weight")
            goals.forEachIndexed { index, goal ->
                val menuItem = popupMenu.menu.add(0, index, 0, goal)
                menuItem.setOnMenuItemClickListener {
                    selectedGoal = goals[it.itemId]
                    binding.resultGoal.text = selectedGoal
                    true
                }
            }
            // Customize PopupMenu appearance (Background and Text Color)
            popupMenu.setForceShowIcon(true)
            popupMenu.show()
        }
    }

    private fun setupGenderDropdown() {
        // Customize PopupMenu for selecting gender
        binding.resultGender.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.resultGender)
            val genders = listOf("Male", "Female")
            genders.forEachIndexed { index, gender ->
                val menuItem = popupMenu.menu.add(0, index, 0, gender)
                menuItem.setOnMenuItemClickListener {
                    selectedGender = genders[it.itemId]
                    binding.resultGender.text = selectedGender
                    true
                }
            }
            // Customize PopupMenu appearance (Background and Text Color)
            popupMenu.setForceShowIcon(true)
            popupMenu.show()
        }
    }

    private fun isInputValid(): Boolean {
        // Validate if all fields are filled
        return !binding.resultUsername.text.isNullOrEmpty() &&
                !binding.resultGender.text.isNullOrEmpty() &&
                !selectedBirthday.isNullOrEmpty() &&
                !selectedGoal.isNullOrEmpty()
    }

    private fun saveData() {
        val username = binding.resultUsername.text.toString()
        val gender = selectedGender
        val birthday = selectedBirthday
        val goal = selectedGoal

        Toast.makeText(this, "Data saved successfully! Username: $username, Gender: $gender, Birthday: $birthday, Goal: $goal", Toast.LENGTH_SHORT).show()
    }
}