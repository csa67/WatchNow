package com.example.tmdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.model.PopularMovies
import com.example.tmdb.model.Response
import com.example.tmdb.repository.MovieRepoInterface
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieViewModel(private val repo: MovieRepoInterface): ViewModel() {

    private val _moviesResponse: MutableLiveData<Response<PopularMovies>> = MutableLiveData();
    val moviesResponse: LiveData<Response<PopularMovies>> = _moviesResponse

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies(){
        viewModelScope.launch {
            _moviesResponse.value = Response.Loading()
            try{
                val data = repo.getPopularMovies()
                if(data == null){
                    _moviesResponse.value = Response.Error("Failed to load movies, try again later.")
                }else {
                    _moviesResponse.value = Response.Success(data)
                }
            } catch (e:Exception){
                _moviesResponse.value = Response.Error(e.message)
            }
        }
    }
}