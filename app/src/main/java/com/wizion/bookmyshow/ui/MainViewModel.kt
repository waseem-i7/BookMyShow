package com.wizion.bookmyshow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wizion.bookmyshow.data.remote.MovieRepository
import com.wizion.bookmyshow.data.local.entity.MovieResponse
import com.wizion.bookmyshow.util.NetworkHelper

class MainViewModel(
    private val networkHelper: NetworkHelper,
    private val movieRepository: MovieRepository
) : ViewModel() {

    companion object {
        private const val API_KEY = "7bc0651fe0ea5973822df3bd27e779d9"
        private const val SOMETHING_WENT_WRONG = "Something went wrong"
    }

    private val _movieResponse = MutableLiveData<MovieResponse>()
    val movieResponse: LiveData<MovieResponse>
        get() = _movieResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse


    fun onCreate() {
        if (networkHelper.isNetworkConnected()) {
            movieRepository.fetchMovies(API_KEY, { movieResponse ->
                _movieResponse.postValue(movieResponse)
            }, {
                _errorResponse.postValue(it)
            })
        }else{
            movieRepository.getMoviesLocal { movieResponse ->  
                if (movieResponse!=null && movieResponse.results.isNotEmpty()){
                    _movieResponse.postValue(movieResponse)
                }else{
                    _errorResponse.postValue(SOMETHING_WENT_WRONG)
                }
            }
        }
    }
}