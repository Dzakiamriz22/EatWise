package com.example.eatwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eatwise.data.NutritionItem
import com.example.eatwise.databinding.ItemResultBinding
import com.example.eatwise.network.PredictionResponse

class ResultAdapter : ListAdapter<NutritionItem, ResultAdapter.ResultViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ResultViewHolder(private val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NutritionItem) {
            // Set the nutrition name (e.g., "Calories")
            binding.name.text = item.name

            // Set the nutrition value (e.g., "175 cm")
            binding.nutrientValue.text = item.value.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NutritionItem>() {
        override fun areItemsTheSame(oldItem: NutritionItem, newItem: NutritionItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: NutritionItem, newItem: NutritionItem): Boolean {
            return oldItem == newItem
        }
    }
}