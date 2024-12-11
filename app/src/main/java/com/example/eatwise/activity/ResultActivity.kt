package com.example.eatwise.activity

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.eatwise.R
import com.example.eatwise.adapter.ResultAdapter
import com.example.eatwise.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }
    private lateinit var resultAdapter: ResultAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Eatwise", Context.MODE_PRIVATE)

        // Set up RecyclerView
        setupAdapter()

        // Load image from Intent
        loadImageFromIntent()

        // Set up Back Button listener
        setupBackButton()
    }

    private fun setupAdapter() {
        resultAdapter = ResultAdapter()

        // Set up RecyclerView
        binding.rvResult.apply {
            adapter = resultAdapter
            layoutManager = LinearLayoutManager(this@ResultActivity)
        }

        // Submit a sample list to the adapter
        resultAdapter.submitList(listOf(5, 6, 7, 8, 9))
    }

    private fun loadImageFromIntent() {
        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = imageUriString?.let { Uri.parse(it) }

        // Load the image using Glide or any other image loading library
        imageUri?.let {
            Glide.with(this)
                .load(it)
                .into(binding.imageView) // Ensure the imageView ID matches in XML
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
