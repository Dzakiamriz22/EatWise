package com.example.eatwise.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eatwise.databinding.ItemCardBinding
import com.example.eatwise.activity.DetailTipsActivity

class TipsAdapter(private val onTipClick: (String) -> Unit) : ListAdapter<String, TipsAdapter.TipsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipsViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TipsViewHolder(binding, onTipClick)
    }

    override fun onBindViewHolder(holder: TipsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TipsViewHolder(
        private val binding: ItemCardBinding,
        private val onTipClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tip: String) {
            binding.tipTextView.text = tip

            // Set up click listener
            itemView.setOnClickListener {
                onTipClick(tip)  // Call the click listener and pass the tip
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
