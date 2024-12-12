package com.example.eatwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eatwise.R
import com.example.eatwise.databinding.ItemRecommendationBinding

class HomeAdapter : ListAdapter<String, HomeAdapter.HomeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HomeViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendation: String) {
            binding.name.text = recommendation
            binding.image.setImageResource(getIconForRecommendation(recommendation))
        }

        private fun getIconForRecommendation(recommendation: String): Int {
            return when {
                recommendation.contains("Carbohydrates", true) -> R.drawable.bg_carbs
                recommendation.contains("Proteins", true) -> R.drawable.bg_protein
                recommendation.contains("Fats", true) -> R.drawable.bg_fruit
                recommendation.contains("Calories", true) -> R.drawable.bg_calories
                recommendation.contains("Water", true) -> R.drawable.bg_water
                else -> R.drawable.bg_water
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}