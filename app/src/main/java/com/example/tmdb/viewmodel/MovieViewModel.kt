package com.example.tmdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.model.PopularMovies
import com.example.tmdb.repository.MovieRepoInterface
import kotlinx.coroutines.launch

class MovieViewModel(private val repo: MovieRepoInterface): ViewModel() {
    private val _movie: MutableLiveData<PopularMovies> = MutableLiveData()
    val movie: LiveData<PopularMovies> = _movie

    init {
        viewModelScope.launch {
            _movie.value = repo.getPopularMovies()

        }
    }
}