package com.wizion.bookmyshow.data.remote

import com.wizion.bookmyshow.data.local.dao.MovieDao
import com.wizion.bookmyshow.data.local.entity.MovieResponse
import com.wizion.bookmyshow.data.remote.retrofit.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepositoryImpl(
    private val movieDao : MovieDao,
    private val request : MovieService
    ) : MovieRepository {

    override fun fetchMovies(api_key: String, onSuccess: (MovieResponse)->Unit, onError: (String) -> Unit) {

        val requestCall = request.getMovies(api_key)

        requestCall.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    //success
                    Thread{
                        movieDao.insertMovies(response.body()!!)
                        onSuccess(response.body()!!)
                    }.start()
                } else if(response.code() == 401) {
                    // "Your session has expired. Please Login again."
                } else {
                    // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //error
                onError("Opps! something went wrong!!")
            }

        })

    }

    override fun getMoviesLocal(onSuccess: (MovieResponse?) -> Unit) {
        Thread{
          onSuccess(movieDao.getMovies())
        }.start()
    }
}