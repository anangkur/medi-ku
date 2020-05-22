package com.anangkur.mediku.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anangkur.mediku.data.local.model.newCovid19.NewCovid19CountryLocalModel
import com.anangkur.mediku.data.local.model.newCovid19.NewCovid19SummaryLocalModel
import com.anangkur.mediku.data.local.model.news.ArticleLocalModel
import com.anangkur.mediku.util.Const

@Database(
    entities = [
        NewCovid19SummaryLocalModel::class,
        NewCovid19CountryLocalModel::class,
        ArticleLocalModel::class
    ],
    version = 8
)
abstract class AppDatabase: RoomDatabase(){

    abstract fun getDao(): AppDao

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        Const.DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}