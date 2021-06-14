package com.fabricio.appnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fabricio.appnews.R
import com.fabricio.appnews.databinding.ItemNewsBinding
import com.fabricio.appnews.model.Article
import kotlinx.android.synthetic.main.item_news.view.*

class MainAdpater : RecyclerView.Adapter<MainAdpater.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder) {
            with(differ.currentList[position]) {
                Glide.with(holder.itemView.context).load(urlToImage).into(binding.ivArticle)
                binding.tvTitle.text = author ?: source?.name
                binding.tvSource.text = source?.name ?: author
                binding.tvDescription.text = description
                binding.tvPublished.text = publishedAt

                val article = differ.currentList[position]
                holder.itemView.setOnClickListener {
                    onItemCickListener?.let { click ->
                        click(article)
                    }
                }
            }
        }

    }

    private var onItemCickListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemCickListener = listener
    }
}