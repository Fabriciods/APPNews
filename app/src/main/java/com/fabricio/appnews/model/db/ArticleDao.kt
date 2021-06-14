package com.fabricio.appnews.model.db

import androidx.room.*
import com.fabricio.appnews.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getAll(): List<Article>

    @Delete
    suspend fun delete(article: Article)
}