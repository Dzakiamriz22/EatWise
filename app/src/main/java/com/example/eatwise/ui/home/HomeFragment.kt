package com.example.eatwise.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.eatwise.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var usernameTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameTextView = view.findViewById(R.id.name)

        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "User Default")

        usernameTextView.text = userName
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "User Default")
        usernameTextView.text = userName
    }
}

