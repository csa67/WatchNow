package com.example.tmdb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val poster: String,
    val description: String,
    val adult: Boolean,
    val releaseYear: String,
    val rating: Float,
    val isFav: Boolean
) : Parcelable
