package com.fabricio.appnews.presenter.news

import com.fabricio.appnews.model.NewsResponse

interface NewsHome {
    interface Presenter {
        fun requestAll()
        fun onSucess(newsResponse: NewsResponse)
        fun onError(message:String)
        fun onComplete()
    }
}