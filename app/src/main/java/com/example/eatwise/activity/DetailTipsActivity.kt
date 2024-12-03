package com.example.eatwise.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val tipContent = intent.getStringExtra("TIP_CONTENT") ?: "No content available"

        val contentTextView = findViewById<TextView>(R.id.tv_content)
        contentTextView.text = tipContent

        val backButton = findViewById<ImageView>(R.id.btn_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val shareButton = findViewById<ImageView>(R.id.btn_share)
        shareButton.setOnClickListener {
            shareTipContent(tipContent)
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
