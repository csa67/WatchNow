package com.example.tmdb.repository

import com.example.tmdb.model.FavMovie
import com.example.tmdb.model.PopularMovies
import kotlinx.coroutines.flow.Flow

interface MovieRepoInterface {

    suspend fun getPopularMovies() : PopularMovies?

}