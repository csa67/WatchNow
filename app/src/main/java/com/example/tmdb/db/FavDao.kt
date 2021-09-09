package com.example.tmdb.db

import androidx.room.*
import com.example.tmdb.model.FavMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {

    @Query("SELECT * FROM favourites order by id DESC")
    fun getFavMoviesList() : Flow<List<FavMovie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavMovie(favMovie: FavMovie)

    @Delete
    suspend fun delFavMovie(favMovie: FavMovie)
}