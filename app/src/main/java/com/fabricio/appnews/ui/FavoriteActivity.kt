package com.fabricio.appnews.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabricio.appnews.R
import com.fabricio.appnews.adapter.MainAdpater
import com.fabricio.appnews.databinding.ActivityFavoriteBinding
import com.fabricio.appnews.model.Article
import com.fabricio.appnews.model.data.NewsDataSource
import com.fabricio.appnews.presenter.ViewHome
import com.fabricio.appnews.presenter.favorite.FavoritePresenter
import com.google.android.material.snackbar.Snackbar


class FavoriteActivity : AppCompatActivity(), ViewHome.Favorite {

    private val mainAdpater by lazy {
        MainAdpater()
    }

    private lateinit var presenter: FavoritePresenter
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val dataSource = NewsDataSource(this)

        presenter = FavoritePresenter(this, dataSource)
        presenter.getAll()
        configRecycle()
        clickAdapter()
        val itemTouchPerCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = mainAdpater.differ.currentList[position]
                presenter.deleteArticle(article)
                Snackbar.make(viewHolder.itemView, R.string.article_delete_successful, Snackbar.LENGTH_SHORT).apply {
                    setAction(getString(R.string.undo)){
                        presenter.saveArticle(article)
                        mainAdpater.notifyDataSetChanged()
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchPerCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }
        presenter.getAll()
    }


    override fun showArticles(articles: List<Article>) {
        mainAdpater.differ.submitList(articles.toList())
    }

    private fun configRecycle() {
        with(binding.rvFavorite) {
            adapter = mainAdpater
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun clickAdapter() {
        mainAdpater.setOnClickListener { article ->
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }
}