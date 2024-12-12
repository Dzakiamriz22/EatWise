package com.example.eatwise.activity

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n", "DefaultLocale")
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
            val confidenceFormatted = String.format("%.2f", response.confidence)
            binding.imageLabel.text = "${response.image_label} (Confidence: $confidenceFormatted%)"
            val nutritionItems = listOf(
                NutritionItem("Calories (kcal)", response.nutrition_info["caloric value"] ?: 0.0),
                NutritionItem("Protein (g)", response.nutrition_info["protein"] ?: 0.0),
                NutritionItem("Carbs (g)", response.nutrition_info["carbohydrates"] ?: 0.0),
                NutritionItem("Fats (g)", response.nutrition_info["fat"] ?: 0.0),
                NutritionItem("Water (ml)", response.nutrition_info["water"] ?: 0.0),
                NutritionItem("Sugar (g)", response.nutrition_info["sugars"] ?: 0.0),
                NutritionItem("Cholesterol (mg)", response.nutrition_info["cholesterol"] ?: 0.0),
                NutritionItem("Calcium (mg)", response.nutrition_info["calcium"] ?: 0.0),
                NutritionItem("Zinc (mg)", response.nutrition_info["zinc"] ?: 0.0),
                NutritionItem("Magnesium (mg)", response.nutrition_info["magnesium"] ?: 0.0),
                NutritionItem("Sodium (mg)", response.nutrition_info["sodium"] ?: 0.0)
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