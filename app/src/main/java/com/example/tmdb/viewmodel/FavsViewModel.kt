package com.example.tmdb.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.example.tmdb.model.FavMovie
import com.example.tmdb.repository.FavRepo
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavViewModel(private val Repo: FavRepo): ViewModel() {

    val favMoviesList: LiveData<List<FavMovie>> = Repo.getFavMoviesList().asLiveData()


    fun addFavMovie(favMovie: FavMovie) {
        viewModelScope.launch{
           Repo.addFavMovie(favMovie)
        }
    }

    fun delFavMovie(favMovie: FavMovie){
        viewModelScope.launch {
            Repo.delFavMovie(favMovie)
        }
    }



}