package com.example.tmdb.repository

import android.util.Log
import com.example.tmdb.R
import com.example.tmdb.db.FavDao
import com.example.tmdb.model.FavMovie
import com.example.tmdb.model.PopularMovies
import com.example.tmdb.networking.RestApiCollection
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class MovieRepoImpl(private val apiService: RestApiCollection):MovieRepoInterface {

    override suspend fun getPopularMovies(): PopularMovies? {

        return try{
            apiService.getMovies("7bfb5935faa90f3b4913f80ed990bc15")
        }catch (e:Exception){
            Log.e("Error",e.stackTraceToString())
            null
        }
    }


}