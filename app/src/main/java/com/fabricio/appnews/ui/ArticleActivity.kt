package com.fabricio.appnews.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.fabricio.appnews.R
import com.fabricio.appnews.databinding.ActivityArticleBinding
import com.fabricio.appnews.model.Article
import com.fabricio.appnews.model.data.NewsDataSource
import com.fabricio.appnews.presenter.ViewHome
import com.fabricio.appnews.presenter.favorite.FavoritePresenter
import com.google.android.material.snackbar.Snackbar


class ArticleActivity : AppCompatActivity(),ViewHome.Favorite{

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        val view  = binding.root
        setContentView(view)
        getArticle()
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this,dataSource)
        binding.wvArticle.apply {
            webViewClient = WebViewClient()
            article.url?.let { url->
                loadUrl(url)
            }
        }
        binding.fbFavorite.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(it,R.string.article_saved_successful,Snackbar.LENGTH_LONG).show()
        }
    }



    private fun getArticle() {
        intent.extras?.let { articleSend ->
            article = articleSend.get("article") as Article
        }
    }

    override fun showArticles(articles: List<Article>) {

    }
}