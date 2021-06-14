package com.fabricio.appnews.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fabricio.appnews.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var _instance: ArticleDatabase? = null
        private val _Lock = Any()

        operator fun invoke(context: Context) = _instance ?: synchronized(_Lock) {
            _instance ?: createdDatabase(context).also { articleDatabase ->
                _instance = articleDatabase
            }
        }
        private fun createdDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "article_db.db"
        ).build()
    }
    }

