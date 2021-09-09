package com.example.tmdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Favourites")
data class FavMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val posterURL: String
)