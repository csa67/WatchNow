package com.example.tmdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.networking.RetrofitBuilder
import com.example.tmdb.repository.MovieRepoImpl
import com.example.tmdb.repository.MovieRepoInterface


class MovieViewModelFactory(private val repo:MovieRepoInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
