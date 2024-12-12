package com.example.eatwise.activity

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.eatwise.R
import com.example.eatwise.adapter.ResultAdapter
import com.example.eatwise.data.NutritionItem
import com.example.eatwise.databinding.ActivityResultBinding
import com.example.eatwise.network.PredictionResponse
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }
    private lateinit var resultAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize the RecyclerView with the ResultAdapter
        resultAdapter = ResultAdapter()

        binding.rvResult.apply {
            layoutManager = LinearLayoutManager(this@ResultActivity)
            adapter = resultAdapter
        }

        // Load the image URI and prediction response from the Intent
        loadImageFromIntent()
        setupBackButton()
    }

    private fun loadImageFromIntent() {
        val imageUriString = intent.getStringExtra("imageUri")
        val predictionResponseJson = intent.getStringExtra("predictionResponse")

        // Convert the prediction response JSON into a PredictionResponse object
        val predictionResponse = predictionResponseJson?.let {
            Gson().fromJson(it, PredictionResponse::class.java)
        }

        // Convert image URI string into URI object
        val imageUri = imageUriString?.let { Uri.parse(it) }

        // Load the image using Glide
        imageUri?.let {
            Glide.with(this)
                .load(it)
                .into(binding.imageView)
        }

        // Display prediction data
        predictionResponse?.let { response ->
            // Set the image label
            binding.imageLabel.text = response.image_label

            // Create a list of the nutrition info to pass to the adapter
            val nutritionItems = listOf(
                NutritionItem("Calories (kcal)", response.nutrition_info["caloric value"] ?: 0.0), // Change unit to kcal
                NutritionItem("Protein (g)", response.nutrition_info["protein"] ?: 0.0), // Change unit to g
                NutritionItem("Carbohydrates (g)", response.nutrition_info["carbohydrates"] ?: 0.0), // Change unit to g
                NutritionItem("Water (ml)", response.nutrition_info["water"] ?: 0.0), // Change unit to ml
                NutritionItem("Calcium (g)", response.nutrition_info["calcium"] ?: 0.0), // Change unit to g
                NutritionItem("Cholesterol (g)", response.nutrition_info["cholesterol"] ?: 0.0), // Change unit to g
                NutritionItem("Copper (g)", response.nutrition_info["copper"] ?: 0.0), // Change unit to g
                NutritionItem("Dietary Fiber (g)", response.nutrition_info["dietary fiber"] ?: 0.0), // Change unit to g
                NutritionItem("Fat (g)", response.nutrition_info["fat"] ?: 0.0), // Change unit to g
                NutritionItem("Iron (g)", response.nutrition_info["iron"] ?: 0.0), // Change unit to g
                NutritionItem("Magnesium (g)", response.nutrition_info["magnesium"] ?: 0.0), // Change unit to g
                NutritionItem("Manganese (g)", response.nutrition_info["manganese"] ?: 0.0), // Change unit to g
                NutritionItem("Monounsaturated Fats (g)", response.nutrition_info["monounsaturated fats"] ?: 0.0), // Change unit to g
                NutritionItem("Nutrition Density", response.nutrition_info["nutrition density"] ?: 0.0),
                NutritionItem("Phosphorus (g)", response.nutrition_info["phosphorus"] ?: 0.0), // Change unit to g
                NutritionItem("Polyunsaturated Fats (g)", response.nutrition_info["polyunsaturated fats"] ?: 0.0), // Change unit to g
                NutritionItem("Potassium (g)", response.nutrition_info["potassium"] ?: 0.0), // Change unit to g
                NutritionItem("Saturated Fats (g)", response.nutrition_info["saturated fats"] ?: 0.0), // Change unit to g
                NutritionItem("Selenium (g)", response.nutrition_info["selenium"] ?: 0.0), // Change unit to g
                NutritionItem("Sodium (g)", response.nutrition_info["sodium"] ?: 0.0), // Change unit to g
                NutritionItem("Sugars (g)", response.nutrition_info["sugars"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin A (g)", response.nutrition_info["vitamin a"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin B1 (g)", response.nutrition_info["vitamin b1"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin B11 (g)", response.nutrition_info["vitamin b11"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin B12 (g)", response.nutrition_info["vitamin b12"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin B2 (g)", response.nutrition_info["vitamin b2"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin B3 (g)", response.nutrition_info["vitamin b3"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin B5 (g)", response.nutrition_info["vitamin b5"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin B6 (g)", response.nutrition_info["vitamin b6"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin C (g)", response.nutrition_info["vitamin c"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin D (g)", response.nutrition_info["vitamin d"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin E (g)", response.nutrition_info["vitamin e"] ?: 0.0), // Change unit to g
                NutritionItem("Vitamin K (g)", response.nutrition_info["vitamin k"] ?: 0.0), // Change unit to g
                NutritionItem("Zinc (g)", response.nutrition_info["zinc"] ?: 0.0) // Change unit to g
            )

            // Pass the nutrition items to the adapter
            resultAdapter.submitList(nutritionItems)
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}