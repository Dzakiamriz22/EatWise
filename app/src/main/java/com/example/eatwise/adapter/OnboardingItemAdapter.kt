package com.example.eatwise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatwise.R
import com.example.eatwise.data.OnboardingItem

class OnboardingItemAdapter(private val onboardingItem: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingItemAdapter.OnboardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        return OnboardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_onboarding,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onboardingItem[position])
    }

    override fun getItemCount(): Int {
        return onboardingItem.size
    }

    inner class OnboardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ivOnboardingImage = view.findViewById<ImageView>(R.id.ivImage)
        private val tvOnboardingTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val tvOnboardingDescription = view.findViewById<TextView>(R.id.tvDesc)

        fun bind(onboardingItem: OnboardingItem) {
            ivOnboardingImage.setImageResource(onboardingItem.ivImage)
            tvOnboardingTitle.text = onboardingItem.tvTitle
            tvOnboardingDescription.text = onboardingItem.tvDesc
        }
    }
}