package com.example.eatwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatwise.R
import com.example.eatwise.data.Article
import com.example.eatwise.databinding.ItemCardBinding

class TipsAdapter(private val onTipClick: (Article) -> Unit) :
    ListAdapter<Article, TipsAdapter.TipsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipsViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TipsViewHolder(binding, onTipClick)
    }

    override fun onBindViewHolder(holder: TipsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TipsViewHolder(
        private val binding: ItemCardBinding,
        private val onTipClick: (Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tipTextView.text = article.title
            Glide.with(binding.root.context)
                .load(article.image_url)
                .placeholder(R.drawable.sample_image)
                .error(R.drawable.sample_image)
                .into(binding.imageView)

            itemView.setOnClickListener { onTipClick(article) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}
