package com.anangkur.mediku.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anangkur.mediku.data.model.covid19.Covid19Data
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.util.Const

@Database(
    entities = [
        Covid19Data::class,
        NewCovid19Summary::class,
        NewCovid19DataCountry::class
    ],
    version = 3
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