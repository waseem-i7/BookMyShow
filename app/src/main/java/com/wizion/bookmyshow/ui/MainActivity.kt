package com.wizion.bookmyshow.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wizion.bookmyshow.data.local.MovieDatabase
import com.wizion.bookmyshow.data.remote.MovieRepositoryImpl
import com.wizion.bookmyshow.databinding.ActivityMainBinding
import com.wizion.bookmyshow.interfaces.ItemClickListener
import com.wizion.bookmyshow.data.local.entity.Movie
import com.wizion.bookmyshow.data.remote.retrofit.MovieService
import com.wizion.bookmyshow.data.remote.retrofit.RetrofitBuilder
import com.wizion.bookmyshow.ui.adapter.MoviesAdapter
import com.wizion.bookmyshow.ui.util.MainViewModelFactory
import com.wizion.bookmyshow.util.NetworkHelper


class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MoviesAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        observeViewModel()
        showMovies()

        binding.pullToRefresh.setOnRefreshListener {
            binding.errorView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            observeViewModel()
            binding.pullToRefresh.setRefreshing(false)
        }
    }

    private fun observeViewModel() {
        viewModel.movieResponse.observe(this, Observer {
              adapter.updateList(it.results)
        })

        viewModel.errorResponse.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            binding.errorView.visibility = View.VISIBLE
            binding.errorView.text = "Please Connect To Internet"
            binding.recyclerView.visibility = View.GONE
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, MainViewModelFactory(
                NetworkHelper(this),
                MovieRepositoryImpl(MovieDatabase.getInstance(this).movieDao(),RetrofitBuilder.buildService(MovieService::class.java))
            )
        )[MainViewModel::class.java]
        viewModel.onCreate()
    }


    private fun showMovies() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MoviesAdapter(this, this)
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClick(item: Movie) {
    }

    override fun onItemClick(position: Int) {
    }

}
