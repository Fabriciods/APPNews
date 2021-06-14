package com.fabricio.appnews.presenter.favorite

import com.fabricio.appnews.model.Article

interface FavoriteHome {
    interface Presenter{
        fun onSuccess(articles: List<Article>)
    }

}