package com.fabricio.appnews.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabricio.appnews.R
import com.fabricio.appnews.adapter.MainAdpater
import com.fabricio.appnews.databinding.ActivityMainBinding
import com.fabricio.appnews.model.Article
import com.fabricio.appnews.model.data.NewsDataSource
import com.fabricio.appnews.presenter.ViewHome
import com.fabricio.appnews.presenter.news.NewsPresenter


class MainActivity : AppCompatActivity(), ViewHome.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: NewsPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val dataSource = NewsDataSource(this)
        presenter = NewsPresenter(this, dataSource)
        presenter.requestAll()
        configRecycle()
        clickAdapter()
    }
    private val mainAdpater by lazy {
        MainAdpater()
    }

    private fun configRecycle() {
        with(binding.rvNews) {
            adapter = mainAdpater
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
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

    override fun showProgressBar() {
        binding.pbNews.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.pbNews.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        mainAdpater.differ.submitList(articles.toList())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_menu -> {
                Intent(this, SearchActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.favorite_menu -> {
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}