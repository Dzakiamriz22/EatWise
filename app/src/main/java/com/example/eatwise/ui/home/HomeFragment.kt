package com.example.eatwise.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.R
import com.example.eatwise.adapter.HomeAdapter
import com.example.eatwise.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("Eatwise", Context.MODE_PRIVATE)

        // Set default username if it's not saved
        binding.username.text = sharedPreferences.getString("name", "User Default")

        // Initialize default values for weight, height, and BMI
        val defaultWeight = 0f
        val defaultHeight = 0f
        sharedPreferences.edit().apply {
            putFloat("weight", defaultWeight)
            putFloat("height", defaultHeight)
            apply()
        }

        setupAdapter()
        setupAutoCalculate()
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter()
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        updateBMIAndRecommendations()
    }

    private fun setupAutoCalculate() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val weightInput = binding.etWeight.text.toString()
                val heightInput = binding.etHeight.text.toString()

                if (weightInput.isNotEmpty() && heightInput.isNotEmpty()) {
                    val weight = weightInput.toFloat()
                    val height = heightInput.toFloat() / 100

                    sharedPreferences.edit().apply {
                        putFloat("weight", weight)
                        putFloat("height", height)
                        apply()
                    }

                    updateBMIAndRecommendations()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etWeight.addTextChangedListener(textWatcher)
        binding.etHeight.addTextChangedListener(textWatcher)
    }

    @SuppressLint("SetTextI18n")
    private fun updateBMIAndRecommendations() {
        val weight = sharedPreferences.getFloat("weight", 0f)
        val height = sharedPreferences.getFloat("height", 0f)

        val bmi = calculateBMI(weight, height)
        val bmiDescription = getBMIDescription(bmi)

        binding.bmiResult.text = "BMI: %.2f".format(bmi)
        binding.bmiDescription.text = bmiDescription

        val recommendations = getRecommendations(bmi)
        homeAdapter.submitList(recommendations)
    }

    private fun calculateBMI(weight: Float, height: Float): Float {
        return if (height > 0) {
            weight / (height * height)
        } else {
            0f // If height is 0, return BMI as 0
        }
    }

    private fun getBMIDescription(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.9 -> "Normal weight"
            bmi in 25.0..29.9 -> "Overweight"
            bmi >= 30 -> "Obesity"
            else -> "-" // Default case
        }
    }

    private fun getRecommendations(bmi: Float): List<String> {
        val recommendations = when {
            bmi < 18.5 -> listOf(
                "Carbohydrates: 300g",
                "Proteins: 70g",
                "Calories: 2500 kcal"
            )
            bmi in 18.5..24.9 -> listOf(
                "Carbohydrates: 250g",
                "Proteins: 60g",
                "Calories: 2000 kcal"
            )
            bmi in 25.0..29.9 -> listOf(
                "Carbohydrates: 200g",
                "Proteins: 50g",
                "Calories: 1800 kcal"
            )
            else -> listOf(
                "Carbohydrates: 150g",
                "Proteins: 40g",
                "Calories: 1500 kcal"
            )
        }

        val dailyWater = when {
            bmi < 18.5 -> "Water: 3.5 liters/day"
            bmi in 18.5..24.9 -> "Water: 3 liters/day"
            bmi in 25.0..29.9 -> "Water: 2.5 liters/day"
            else -> "Water: 2 liters/day"
        }

        return recommendations + dailyWater
    }
}