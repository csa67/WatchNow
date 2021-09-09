package com.example.tmdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.networking.RetrofitBuilder
import com.example.tmdb.repository.FavRepo
import com.example.tmdb.repository.FavRepoImpl


class FavViewModelFactory(private val repo:FavRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}