package com.satya.movee.viewmodel.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.constants.Constant.Companion.duringTheApiCall
import com.satya.movee.model.popular.PopularPersonModel
import com.satya.movee.model.trendingMovies.TrendingMovieList
import com.satya.movee.model.trendingTvShows.TrendingTvShowsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularViewModel (private val repository: MoviesRepositories): ViewModel()  {

    val popularPerson = MutableLiveData<PopularPersonModel>()
    val getUpcomingMovies = MutableLiveData<TrendingMovieList>()
    val getAllTrendingTvShowsWeek = MutableLiveData<TrendingTvShowsList>()
    val trendingMovieList = MutableLiveData<TrendingMovieList>()
    val errorMessage = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()

    private fun apiCallFinished() {
        isLoading.postValue(true)
    }

    fun getAllPopularPerson(page: Int) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPopularPersons(page)
            response.enqueue(object : Callback<PopularPersonModel> {
                override fun onResponse(call: Call<PopularPersonModel>, response: Response<PopularPersonModel>) {
                    apiCallFinished()
                    popularPerson.postValue(response.body())
                }
                override fun onFailure(call: Call<PopularPersonModel>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getUpcomingMovies() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUpcomingMovies()
            response.enqueue(object : Callback<TrendingMovieList> {
                override fun onResponse(call: Call<TrendingMovieList>, response: Response<TrendingMovieList>) {
                    apiCallFinished()
                    getUpcomingMovies.postValue(response.body())
                }
                override fun onFailure(call: Call<TrendingMovieList>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getAllTrendingTvShowsThisWeek() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllTrendingDayTvShows()
            response.enqueue(object : Callback<TrendingTvShowsList> {
                override fun onResponse(call: Call<TrendingTvShowsList>, response: Response<TrendingTvShowsList>) {
                    apiCallFinished()
                    getAllTrendingTvShowsWeek.postValue(response.body())
                }
                override fun onFailure(call: Call<TrendingTvShowsList>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getAllTrendingMovies() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllTrendingTodayMovies()
            response.enqueue(object : Callback<TrendingMovieList> {
                override fun onResponse(call: Call<TrendingMovieList>, response: Response<TrendingMovieList>) {
                    apiCallFinished()
                    trendingMovieList.postValue(response.body())
                }
                override fun onFailure(call: Call<TrendingMovieList>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

}