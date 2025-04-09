package com.example.tmdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdb.model.FavMovie
import com.example.tmdb.repository.FavRepo
import kotlinx.coroutines.launch

class FavViewModel(private val repo: FavRepo): ViewModel() {

    lateinit var favMoviesList: LiveData<List<FavMovie>>

    init{
        viewModelScope.launch {
            favMoviesList = repo.getFavMoviesList().asLiveData()
        }
    }

    fun addFavMovie(favMovie: FavMovie) {
        viewModelScope.launch{
           repo.addFavMovie(favMovie)
        }
    }

    fun delFavMovie(favMovie: FavMovie){
        viewModelScope.launch {
            repo.delFavMovie(favMovie)
        }
    }



}