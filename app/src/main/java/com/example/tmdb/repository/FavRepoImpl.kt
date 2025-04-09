package com.example.tmdb.repository

import com.example.tmdb.db.FavDao
import com.example.tmdb.model.FavMovie
import kotlinx.coroutines.flow.Flow

class FavRepoImpl(private val dao: FavDao):FavRepo {

    override suspend fun addFavMovie(favMovie: FavMovie) {
        dao.addFavMovie(favMovie)
    }

    override suspend fun getFavMoviesList(): Flow<List<FavMovie>> {
        return dao.getFavMoviesList()
    }

    override suspend fun delFavMovie(favMovie: FavMovie){
        dao.delFavMovie(favMovie)
    }
}