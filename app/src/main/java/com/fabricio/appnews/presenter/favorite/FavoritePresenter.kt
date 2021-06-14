package com.fabricio.appnews.presenter.favorite

import com.fabricio.appnews.model.Article
import com.fabricio.appnews.model.data.NewsDataSource
import com.fabricio.appnews.presenter.ViewHome

class FavoritePresenter(val view: ViewHome.Favorite, private val dataSource: NewsDataSource) :
    FavoriteHome.Presenter {

    fun getAll(){
        this.dataSource.getAllArticle(this)
    }
    fun saveArticle(article: Article) {
        this.dataSource.saveArticle(article)
    }
    fun deleteArticle(article: Article){
        this.dataSource.deleteArticle(article)

    }

    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }
}