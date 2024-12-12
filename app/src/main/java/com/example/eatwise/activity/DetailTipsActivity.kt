package com.example.eatwise.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.eatwise.R

class DetailTipsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_tips)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tipTitle = intent.getStringExtra("TIP_TITLE") ?: "No Title"
        val tipContent = intent.getStringExtra("TIP_CONTENT") ?: "No Content"
        val tipImageUrl = intent.getStringExtra("TIP_IMAGE_URL") ?: ""
        val timestamp = intent.getStringExtra("TIP_TIMESTAMP") ?: "No Timestamp"

        val titleTextView = findViewById<TextView>(R.id.tv_title)
        val contentTextView = findViewById<TextView>(R.id.tv_content)
        val timestampTextView = findViewById<TextView>(R.id.tv_timestamp)
        val imageView = findViewById<ImageView>(R.id.img_header)

        titleTextView.text = tipTitle
        contentTextView.text = tipContent
        timestampTextView.text = timestamp

        Glide.with(this)
            .load(tipImageUrl)
            .placeholder(R.drawable.sample_image)
            .error(R.drawable.sample_image)
            .into(imageView)

        val backButton = findViewById<ImageView>(R.id.btn_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val shareButton = findViewById<ImageView>(R.id.btn_share)
        shareButton.setOnClickListener {
            shareTipContent("$tipTitle\n\n$tipContent")
        }
    }

    private fun shareTipContent(content: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}