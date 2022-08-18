package com.satya.movee.viewmodel.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.constants.Constant
import com.satya.movee.model.trendingMovies.TrendingMovieList
import com.satya.movee.model.trendingTvShows.TrendingTvShowsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowsViewModel (private val repository: MoviesRepositories): ViewModel()  {

    val trendingMovieList = MutableLiveData<TrendingTvShowsList>()
    val errorMessage = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()

    private fun apiCallFinished() {
        isLoading.postValue(true)
    }
    private fun duringTheApiCall() {
        isLoading.postValue(false)
    }

    fun getAllTrendingTvShows() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllTrendingTvShows()
            response.enqueue(object : Callback<TrendingTvShowsList> {
                override fun onResponse(call: Call<TrendingTvShowsList>, response: Response<TrendingTvShowsList>) {
                    apiCallFinished()
                    trendingMovieList.postValue(response.body())
                }
                override fun onFailure(call: Call<TrendingTvShowsList>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }
}