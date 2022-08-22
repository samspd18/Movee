package com.satya.movee.ui.fragment.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.satya.movee.R
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.constants.Constant.Companion.imageBaseUrl
import com.satya.movee.databinding.FragmentMovieBinding
import com.satya.movee.network.RetrofitInstance
import com.satya.movee.ui.adapter.movies.MovieAdapter
import com.satya.movee.ui.adapter.movies.MovieAllTimeFavoriteAdapter
import com.satya.movee.ui.adapter.movies.MovieLatestAdapter
import com.satya.movee.ui.adapter.movies.MoviePopularAdapter
import com.satya.movee.viewmodel.ViewModel.MoviesViewModel
import com.satya.movee.viewmodel.ViewModelFactory.MoviesViewModelFactory
import kotlin.random.Random

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private lateinit var viewModel: MoviesViewModel
    private val retrofitService = RetrofitInstance.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = MovieAdapter()
    private val latestAdapter = MovieLatestAdapter()
    private val allTimeFavoriteMovieAdapter = MovieAllTimeFavoriteAdapter()
    private val popularMoviesAdapter = MoviePopularAdapter()
    private lateinit var navBar: BottomNavigationView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this, MoviesViewModelFactory(MoviesRepositories(retrofitService)))[MoviesViewModel::class.java]

        getAllTrendingMovies()
        getAllLatestMovies()
        getAllTimeFavoriteMovies()
        getAllPopularMovies()

        loadLoader()

        viewModel.getAllTrendingMovies()
        viewModel.getAllLatestMovies()
        viewModel.getAllTimeFavoritesMovies()
        viewModel.getAllPopularMovies()

        binding.search.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("media-type","movie")

            val nav = findNavController()
            nav.navigate(R.id.navigation_search,bundle)
        }


        return root
    }

    private fun loadLoader() {
        viewModel.isLoading.observe(viewLifecycleOwner
        ) { isLoading ->

            if (isLoading != null) {
                if (isLoading) {
                    // hide your progress bar
                    binding.loader.visibility = View.GONE
                    binding.innerLayout.visibility = View.VISIBLE
                } else if(!isLoading) {
                   // binding.innerLayout.visibility = View.GONE
                    binding.loader.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getAllTrendingMovies() {
        binding.trendingRecyclerView.adapter = adapter
        viewModel.trendingMovieList.observe(viewLifecycleOwner) {
            val index = Random.nextInt(0,it?.results!!.size - 1)
            Glide.with(this)
                .load(imageBaseUrl+ it.results[index]?.posterPath)
                .placeholder(R.drawable.black_background)
                .into(binding.MainPoster)
            "Trending in #${index+1}".also { binding.trendingPosition.text = it }
            val id = it.results[index]?.id!!

            binding.info.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id",id)
                val nav = binding.root.findNavController()
                nav.navigate(R.id.navigation_detail,bundle)
            }
            val rating = String.format("%.1f", it.results[index]?.voteAverage).toDouble() / 2
            "Rating: $rating / 5".also { binding.rating.text = it }
            adapter.setMovieList(it.results)
        }

        errorMessage()

    }


    private fun getAllLatestMovies() {
        binding.latestRecyclerView.adapter = latestAdapter
        viewModel.latestMoviesList.observe(viewLifecycleOwner) {
            latestAdapter.setLatestMovieList(it.results)
        }
        errorMessage()
    }

    private fun getAllTimeFavoriteMovies() {
        binding.allTImeFavoriteMoviesRecyclerview.adapter = allTimeFavoriteMovieAdapter
        viewModel.allTimeFavoriteMovieList.observe(viewLifecycleOwner) {
            allTimeFavoriteMovieAdapter.setAllTimeFavoriteMovies(it.results)
        }
        errorMessage()
    }

    private fun getAllPopularMovies() {
        binding.popularMoviesRecyclerView.adapter = popularMoviesAdapter
        viewModel.allPopularMovies.observe(viewLifecycleOwner) {
            popularMoviesAdapter.setLatestMovieList(it.results)
        }

        errorMessage()
    }

    private fun errorMessage() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        navBar= requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.VISIBLE
    }
    override fun onPause() {
        super.onPause()
        navBar= requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}