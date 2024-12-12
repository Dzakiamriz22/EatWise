package com.example.eatwise.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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

        resultAdapter = ResultAdapter()

        binding.rvResult.apply {
            layoutManager = LinearLayoutManager(this@ResultActivity)
            adapter = resultAdapter
        }

        loadImageFromIntent()
        setupButton()
    }

    private fun loadImageFromIntent() {
        val imageUriString = intent.getStringExtra("imageUri")
        val predictionResponseJson = intent.getStringExtra("predictionResponse")

        val predictionResponse = predictionResponseJson?.let {
            Gson().fromJson(it, PredictionResponse::class.java)
        }

        val imageUri = imageUriString?.let { Uri.parse(it) }
        imageUri?.let {
            Glide.with(this)
                .load(it)
                .into(binding.imageView)
        }

        predictionResponse?.let { response ->
            binding.imageLabel.text = response.image_label
            val nutritionItems = listOf(
                NutritionItem("Calories (kcal)", response.nutrition_info["caloric value"] ?: 0.0),
                NutritionItem("Protein (g)", response.nutrition_info["protein"] ?: 0.0),
                NutritionItem("Carbohydrates (g)", response.nutrition_info["carbohydrates"] ?: 0.0),
                NutritionItem("Water (ml)", response.nutrition_info["water"] ?: 0.0),
                NutritionItem("Calcium (g)", response.nutrition_info["calcium"] ?: 0.0),
                NutritionItem("Cholesterol (g)", response.nutrition_info["cholesterol"] ?: 0.0),
                NutritionItem("Copper (g)", response.nutrition_info["copper"] ?: 0.0),
                NutritionItem("Dietary Fiber (g)", response.nutrition_info["dietary fiber"] ?: 0.0),
                NutritionItem("Fat (g)", response.nutrition_info["fat"] ?: 0.0),
                NutritionItem("Iron (g)", response.nutrition_info["iron"] ?: 0.0),
                NutritionItem("Magnesium (g)", response.nutrition_info["magnesium"] ?: 0.0),
                NutritionItem("Manganese (g)", response.nutrition_info["manganese"] ?: 0.0),
                NutritionItem("Monounsaturated Fats (g)", response.nutrition_info["monounsaturated fats"] ?: 0.0),
                NutritionItem("Nutrition Density", response.nutrition_info["nutrition density"] ?: 0.0),
                NutritionItem("Phosphorus (g)", response.nutrition_info["phosphorus"] ?: 0.0),
                NutritionItem("Polyunsaturated Fats (g)", response.nutrition_info["polyunsaturated fats"] ?: 0.0),
                NutritionItem("Potassium (g)", response.nutrition_info["potassium"] ?: 0.0),
                NutritionItem("Saturated Fats (g)", response.nutrition_info["saturated fats"] ?: 0.0),
                NutritionItem("Selenium (g)", response.nutrition_info["selenium"] ?: 0.0),
                NutritionItem("Sodium (g)", response.nutrition_info["sodium"] ?: 0.0),
                NutritionItem("Sugars (g)", response.nutrition_info["sugars"] ?: 0.0),
                NutritionItem("Vitamin A (g)", response.nutrition_info["vitamin a"] ?: 0.0),
                NutritionItem("Vitamin B1 (g)", response.nutrition_info["vitamin b1"] ?: 0.0),
                NutritionItem("Vitamin B11 (g)", response.nutrition_info["vitamin b11"] ?: 0.0),
                NutritionItem("Vitamin B12 (g)", response.nutrition_info["vitamin b12"] ?: 0.0),
                NutritionItem("Vitamin B2 (g)", response.nutrition_info["vitamin b2"] ?: 0.0),
                NutritionItem("Vitamin B3 (g)", response.nutrition_info["vitamin b3"] ?: 0.0),
                NutritionItem("Vitamin B5 (g)", response.nutrition_info["vitamin b5"] ?: 0.0),
                NutritionItem("Vitamin B6 (g)", response.nutrition_info["vitamin b6"] ?: 0.0),
                NutritionItem("Vitamin C (g)", response.nutrition_info["vitamin c"] ?: 0.0),
                NutritionItem("Vitamin D (g)", response.nutrition_info["vitamin d"] ?: 0.0),
                NutritionItem("Vitamin E (g)", response.nutrition_info["vitamin e"] ?: 0.0),
                NutritionItem("Vitamin K (g)", response.nutrition_info["vitamin k"] ?: 0.0),
                NutritionItem("Zinc (g)", response.nutrition_info["zinc"] ?: 0.0)
            )
            resultAdapter.submitList(nutritionItems)
        }
    }

    private fun setupButton() {
        binding.actionImage.setOnClickListener {
            finish()
        }
    }
}