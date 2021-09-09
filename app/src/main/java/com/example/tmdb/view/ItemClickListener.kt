package com.example.tmdb.view

import com.example.tmdb.model.Result

interface ItemClickListener {

    fun onClick(position: Int, result: Result)
}