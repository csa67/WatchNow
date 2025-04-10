package com.example.tmdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdb.model.FavMovie
import com.example.tmdb.repository.FavRepo
import kotlinx.coroutines.launch

class FavViewModel(private val repo: FavRepo): ViewModel() {

    lateinit var favMoviesList: LiveData<List<FavMovie>>

    private var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage
    init{
        viewModelScope.launch {
            favMoviesList = repo.getFavMoviesList().asLiveData()
        }
    }

    fun addFavMovie(favMovie: FavMovie) {
        viewModelScope.launch{
           repo.addFavMovie(favMovie)
            _toastMessage.value = "Added movie to favourites"
        }
    }

    fun delFavMovie(favMovie: FavMovie){
        viewModelScope.launch {
            repo.delFavMovie(favMovie)
            _toastMessage.value = "Removed movie from favourites"
        }
    }



}