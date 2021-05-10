package com.example.covid100.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covid100.data.response.ArticlesResponse
import com.example.covid100.databinding.HolderNewsItemBinding

class NewsAdapter(
    private val itemClick: (url: String) -> Unit
) : ListAdapter<ArticlesResponse, NewsAdapter.NewsViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ArticlesResponse>() {
            override fun areItemsTheSame(
                oldItem: ArticlesResponse,
                newItem: ArticlesResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticlesResponse,
                newItem: ArticlesResponse
            ): Boolean {
                return oldItem.newsUrl == newItem.newsUrl
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderNewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.populateViews(article, itemClick)
    }

    class NewsViewHolder(private val binding: HolderNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateViews(item: ArticlesResponse, itemClick: (url: String) -> Unit){
            binding.apply {
                tvNewsTitle.text = item.title
                Glide.with(binding.root)
                    .load(item.imgUrl)
                    .into(binding.ivNewsImage)
            }

            binding.root.setOnClickListener {
                item.newsUrl?.let { url ->
                    itemClick.invoke(url)
                }
            }
        }
    }
}