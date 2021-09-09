package com.example.tmdb.networking

import com.example.tmdb.model.PopularMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiCollection {

    @GET("/3/movie/popular")
    suspend fun getMovies(@Query("api_key")key:String): PopularMovies?
}