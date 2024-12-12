package com.example.eatwise.activity

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.R
import com.example.eatwise.data.User
import com.example.eatwise.data.repository.UserRepository
import com.example.eatwise.databinding.ActivityEditBinding
import com.example.eatwise.network.ApiClient
import com.example.eatwise.ui.profile.ProfileViewModel
import com.example.eatwise.util.DateHelper
import com.example.eatwise.viewmodel.UserViewModelFactory
import java.util.Calendar

class EditActivity : AppCompatActivity(R.layout.activity_edit) {

    private val binding by viewBinding(ActivityEditBinding::bind)
    private lateinit var sharedPreferences:SharedPreferences
    private var selectedGoal: String? = null
    private var selectedBirthday: String? = null
    private var selectedGender: String? = null
    private val profileViewModel by lazy {
        val factory = UserViewModelFactory(UserRepository(ApiClient.apiService))
        ViewModelProvider(this,factory)[ProfileViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("Eatwise", MODE_PRIVATE)
        setupBirthdayPicker()
        setupGoalDropdown()
        setupGenderDropdown()
        profileViewModel.getUserProfile(sharedPreferences.getString("uid", "")!!)
        setupObserver()

        binding.actionImage.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            if (isInputValid()) {
                saveData()
                Toast.makeText(this, "Akun berhasil terupdate", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupBirthdayPicker() {
        binding.resultBirthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedBirthday = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                binding.resultBirthday.text = selectedBirthday
            }, year, month, day).apply {
                setTitle("Select Birthday")
            }.show()
        }
    }

    private fun setupGoalDropdown() {
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
            popupMenu.setForceShowIcon(true)
            popupMenu.show()
        }
    }

    private fun setupGenderDropdown() {
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
            popupMenu.setForceShowIcon(true)
            popupMenu.show()
        }
    }

    private fun isInputValid(): Boolean {
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

        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if (gender.isNullOrEmpty()) {
            Toast.makeText(this, "Please select a gender!", Toast.LENGTH_SHORT).show()
            return
        }

        if (birthday.isNullOrEmpty()) {
            Toast.makeText(this, "Please select a birthday!", Toast.LENGTH_SHORT).show()
            return
        }

        if (goal.isNullOrEmpty()) {
            Toast.makeText(this, "Please select a goal!", Toast.LENGTH_SHORT).show()
            return
        }

        profileViewModel.editUserProfile(
            sharedPreferences.getString("uid", "")!!,
            User(
                sharedPreferences.getString("uid", "")!!,
                username,
                DateHelper.calculateAge(birthday),
                gender,
                0f,
                0f,
                goal
            )
        )

        val editor = sharedPreferences.edit()
        editor.putString("name", username)
        editor.apply()

        Toast.makeText(this, "Profile successfully updated!", Toast.LENGTH_SHORT).show()
    }

    private fun setupObserver() {
        profileViewModel.userProfile.observe(this) { userProfile ->
            if (userProfile.name.isEmpty()) {
                binding.resultUsername.setText(sharedPreferences.getString("name", ""))
            } else {
                binding.resultUsername.setText(userProfile.name)
            }
            binding.resultGender.text = userProfile.gender
            binding.resultBirthday.text = userProfile.age.toString()
            binding.resultGoal.text = userProfile.eat_goal

            selectedGender = userProfile.gender
            selectedBirthday = userProfile.age.toString()
            selectedGoal = userProfile.eat_goal
        }

        profileViewModel.exception.observe(this) { exception ->
            if (exception) {
                Toast.makeText(this, "Exception occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }
}