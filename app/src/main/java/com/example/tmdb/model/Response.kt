package com.example.tmdb.model

sealed class Response<T>{
    class Success<T>(val data:T) : Response<T>()
    class Error<T>(val errorMessage: String?):Response<T>()
    class Loading<T> : Response<T>()
}
