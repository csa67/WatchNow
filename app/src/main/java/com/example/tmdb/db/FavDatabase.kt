package com.example.tmdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tmdb.model.FavMovie


@Database(entities = [FavMovie::class],version =1, exportSchema = false)
abstract class FavDataBase: RoomDatabase() {

    abstract fun favDao(): FavDao
    companion object{

        @Volatile
        private var INSTANCE: FavDataBase?= null

        fun getDatabase(context: Context): FavDataBase{

            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavDataBase::class.java,
                    "fav_movies_db"
                ).build()
                INSTANCE = instance

                instance
            }
        }

    }
}