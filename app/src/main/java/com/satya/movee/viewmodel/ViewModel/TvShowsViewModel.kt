package com.satya.movee.viewmodel.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.model.trendingMovies.TrendingMovieList
import com.satya.movee.model.trendingTvShows.TrendingTvShowsList

class TvShowsViewModel (private val repository: MoviesRepositories): ViewModel()  {

    val trendingMovieList = MutableLiveData<TrendingTvShowsList>()
    val errorMessage = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()
}