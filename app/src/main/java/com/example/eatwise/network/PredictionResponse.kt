package com.example.eatwise.network

data class PredictionResponse(
    val confidence: Double,
    val image_label: String,
    val nutrition_info: Map<String, Any>
)
