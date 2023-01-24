package com.wizion.bookmyshow.data.remote

import com.wizion.bookmyshow.data.local.entity.MovieResponse

interface MovieRepository {
    fun fetchMovies(api_key : String, onSuccess:(MovieResponse)->Unit, onError:(String)->Unit)

    fun getMoviesLocal(onSuccess: (MovieResponse?) -> Unit)
}