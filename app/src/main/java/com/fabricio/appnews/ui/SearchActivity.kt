package com.fabricio.appnews.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabricio.appnews.adapter.MainAdpater
import com.fabricio.appnews.databinding.ActivitySearchBinding
import com.fabricio.appnews.model.Article
import com.fabricio.appnews.model.data.NewsDataSource
import com.fabricio.appnews.presenter.ViewHome
import com.fabricio.appnews.presenter.search.SearchPresenter
import com.fabricio.appnews.util.UtilQueryTextListener


class SearchActivity : AppCompatActivity(), ViewHome.View {

    private val mainAdpater by lazy {
        MainAdpater()
    }
    private lateinit var presenter: SearchPresenter
    private lateinit var binding: ActivitySearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val dataSource = NewsDataSource(this)
        presenter = SearchPresenter(this, dataSource)
        search()
        configRecycle()
        clickAdapter()
    }


    private fun search() {
        binding.svSearch.setOnQueryTextListener(
            UtilQueryTextListener(this.lifecycle) { newText ->
                newText?.let { query ->
                    if (query.isNotEmpty()) {
                        presenter.search(query)
                        binding.pbSearch.visibility = View.VISIBLE
                    }
                }

            }
        )
    }

    private fun configRecycle() {
        with(binding.rvSearch) {
            adapter = mainAdpater
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(
                DividerItemDecoration(this@SearchActivity, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun clickAdapter() {
        mainAdpater.setOnClickListener { article ->
            val intent = Intent(this,ArticleActivity::class.java)
            intent.putExtra("article",article)
            startActivity(intent)
        }
    }

    override fun showProgressBar() {
        binding.pbSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.pbSearch.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        mainAdpater.differ.submitList(articles.toList())
    }
}