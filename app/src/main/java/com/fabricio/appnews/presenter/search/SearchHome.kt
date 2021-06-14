package com.fabricio.appnews.presenter.search

import com.fabricio.appnews.model.NewsResponse

interface SearchHome {
    interface Presenter {
        fun search(term: String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}