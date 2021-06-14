package com.fabricio.appnews.model.data

import com.fabricio.appnews.model.Article
import com.fabricio.appnews.model.db.ArticleDatabase

class NewsRepository(private val _db: ArticleDatabase) {
    suspend fun updateInsert(article: Article) = _db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = _db.getArticleDao().getAll()

    suspend fun delete(article: Article) = _db.getArticleDao().delete(article)
}