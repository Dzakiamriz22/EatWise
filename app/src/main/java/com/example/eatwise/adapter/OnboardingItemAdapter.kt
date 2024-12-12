package com.example.eatwise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatwise.R
import com.example.eatwise.data.OnboardingItem

class OnboardingItemAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingItemAdapter.OnboardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_onboarding,
            parent,
            false
        )
        return OnboardingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnboardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageView: ImageView = view.findViewById(R.id.ivImage)
        private val titleTextView: TextView = view.findViewById(R.id.tvTitle)
        private val descriptionTextView: TextView = view.findViewById(R.id.tvDesc)

        fun bind(onboardingItem: OnboardingItem) {
            imageView.setImageResource(onboardingItem.ivImage)
            titleTextView.text = onboardingItem.tvTitle
            descriptionTextView.text = onboardingItem.tvDesc
        }
    }
}