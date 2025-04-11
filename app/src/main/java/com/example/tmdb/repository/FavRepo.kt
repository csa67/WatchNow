package com.example.tmdb.repository

import com.example.tmdb.model.FavMovie
import kotlinx.coroutines.flow.Flow

interface FavRepo {

    suspend fun addFavMovie(favMovie: FavMovie)
    fun getFavMoviesList() : Flow<List<FavMovie>>
    suspend fun delFavMovie(favMovie: FavMovie)
}