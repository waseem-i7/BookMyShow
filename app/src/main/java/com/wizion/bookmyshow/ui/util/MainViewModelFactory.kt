package com.wizion.bookmyshow.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wizion.bookmyshow.data.remote.MovieRepository
import com.wizion.bookmyshow.ui.MainViewModel
import com.wizion.bookmyshow.util.NetworkHelper

class MainViewModelFactory(private val networkHelper: NetworkHelper,
                           private val movieRepository: MovieRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(networkHelper, movieRepository) as T
    }
}