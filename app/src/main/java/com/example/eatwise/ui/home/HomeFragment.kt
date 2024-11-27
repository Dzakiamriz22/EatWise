package com.example.eatwise.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
        sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        binding.username.text = sharedPreferences.getString("username", "User Default")

        setupAdapter()
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter()
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        homeAdapter.submitList(listOf(5, 6, 7, 8, 9))
    }
}

