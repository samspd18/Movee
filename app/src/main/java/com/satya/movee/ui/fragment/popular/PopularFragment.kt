package com.satya.movee.ui.fragment.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.satya.movee.R
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.databinding.FragmentPopularBinding
import com.satya.movee.network.RetrofitInstance
import com.satya.movee.ui.adapter.movies.MovieAdapter
import com.satya.movee.ui.adapter.popular.PopularPersonAdapter
import com.satya.movee.ui.adapter.popular.UpCominGMoviesAdapter
import com.satya.movee.ui.adapter.popular.TrendingThisDayAdapter
import com.satya.movee.viewmodel.ViewModel.PopularViewModel
import com.satya.movee.viewmodel.ViewModelFactory.PopularViewModelFacory

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding?= null
    private lateinit var viewModel: PopularViewModel
    private val retrofitService = RetrofitInstance.getInstance()
    private val binding get() = _binding!!
    private lateinit var navBar: BottomNavigationView

    private var popularPersonAdapter = PopularPersonAdapter()
    private var upComingAdapter = UpCominGMoviesAdapter()
    private var thisDayTrendingTvSeries = TrendingThisDayAdapter()
    private val thisDayTrendingMovies = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, PopularViewModelFacory(MoviesRepositories(retrofitService)))[PopularViewModel::class.java]

        val pageNumber : Int = (1..45).random()
        popularPerson()
        upComingMovies()
        todayTrendingTvSeries()
        todayTrendingMovies()
        loadLoader()


        viewModel.getAllPopularPerson(pageNumber)
        viewModel.getUpcomingMovies()
        viewModel.getAllTrendingTvShowsThisWeek()
        viewModel.getAllTrendingMovies()

        return binding.root
    }

    private fun loadLoader() {
        viewModel.isLoading.observe(viewLifecycleOwner
        ) { isLoading ->

            if (isLoading != null) {
                if (isLoading) {
                    // hide your progress bar
                    binding.popularLoader.visibility = View.GONE
//                    binding.innerLayout.visibility = View.VISIBLE
                } else if(!isLoading) {
                    // binding.innerLayout.visibility = View.GONE
                    binding.popularLoader.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun upComingMovies() {
        binding.upcomingMovieRecyclerView.adapter = upComingAdapter
        viewModel.getUpcomingMovies.observe(viewLifecycleOwner) {
            upComingAdapter.setMovieList(it.results)
        }
    }

    private fun popularPerson() {
        binding.popularRecyclerView.adapter = popularPersonAdapter
        viewModel.popularPerson.observe(viewLifecycleOwner) {
            popularPersonAdapter.setPopularPeople(it.results)
        }
    }

    private fun todayTrendingTvSeries() {
        binding.upComingTvSeriesRecyclerView.adapter = thisDayTrendingTvSeries
        viewModel.getAllTrendingTvShowsWeek.observe(viewLifecycleOwner) {
            thisDayTrendingTvSeries.setMovieList(it.results)
        }
    }

    private fun todayTrendingMovies() {
        binding.MoviesOfThisDayRecyclerView.adapter = thisDayTrendingMovies
        viewModel.trendingMovieList.observe(viewLifecycleOwner) {
            thisDayTrendingMovies.setMovieList(it.results)
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