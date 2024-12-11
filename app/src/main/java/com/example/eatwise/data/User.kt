package com.example.eatwise.data

data class User(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val height: Float,
    val weight: Float,
    val eat_goal: String,
    val timestamp: String? = null
)
