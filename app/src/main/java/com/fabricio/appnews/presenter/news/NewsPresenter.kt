package com.fabricio.appnews.presenter.news

import com.fabricio.appnews.model.NewsResponse
import com.fabricio.appnews.model.data.NewsDataSource
import com.fabricio.appnews.presenter.ViewHome

class NewsPresenter (
    val view: ViewHome.View,
    private val dataSource: NewsDataSource):NewsHome.Presenter {
    override fun requestAll() {
        this.view.showProgressBar()
        this.dataSource.getBreakingNews(this)
    }

    override fun onSucess(newsResponse: NewsResponse) {
        this.view.showArticles(newsResponse.articles)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)

    }

    override fun onComplete() {
        this.view.hideProgressBar()
    }
}