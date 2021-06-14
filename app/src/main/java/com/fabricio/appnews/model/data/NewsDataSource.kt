package com.fabricio.appnews.model.data

import android.content.Context
import com.fabricio.appnews.model.Article
import com.fabricio.appnews.model.db.ArticleDatabase
import com.fabricio.appnews.network.RetrofitInstance
import com.fabricio.appnews.presenter.favorite.FavoriteHome
import com.fabricio.appnews.presenter.news.NewsHome
import com.fabricio.appnews.presenter.search.SearchHome
import kotlinx.coroutines.*
import retrofit2.Retrofit

class NewsDataSource(context: Context) {
    private var _db: ArticleDatabase = ArticleDatabase(context)
    private var newsRepository: NewsRepository = NewsRepository(_db)
    fun getBreakingNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakNews("br")
            if (response.isSuccessful) {
                response.body()?.let { newResponse ->
                    callback.onSucess(newResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun searchNews(term: String, callback: SearchHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.searchNews(term)
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)

                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }
    fun saveArticle(article:Article){
        GlobalScope.launch(Dispatchers.Main){
            newsRepository.updateInsert(article)
        }
    }
    fun getAllArticle(callback: FavoriteHome.Presenter){
        var allArticle: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticle = newsRepository.getAll()

            withContext(Dispatchers.Main){
                callback.onSuccess(allArticle)
            }
        }
    }
    fun deleteArticle(article: Article?){
        GlobalScope.launch(Dispatchers.Main){
            article?.let { articleSafe->
                newsRepository.delete(articleSafe)
            }
        }
    }
}