package com.satya.movee.viewmodel.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.constants.Constant.Companion.apiCallFinished
import com.satya.movee.constants.Constant.Companion.duringTheApiCall
import com.satya.movee.model.search.movie.SearchMovieModel
import com.satya.movee.model.trendingMovies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel (private val repository: MoviesRepositories): ViewModel() {

    val trendingMovieList = MutableLiveData<TrendingMovieList>()
    val latestMoviesList = MutableLiveData<TrendingMovieList>()
    val allTimeFavoriteMovieList = MutableLiveData<TrendingMovieList>()
    val allPopularMovies = MutableLiveData<TrendingMovieList>()
    val getMovieDetail = MutableLiveData<MovieDetailModel>()
    val getMovieVideo = MutableLiveData<MovieVideo>()
    val getMovieCredits = MutableLiveData<MovieCredits>()
    val getSimilarMovies = MutableLiveData<TrendingMovieList>()
    val getSearchResult = MutableLiveData<SearchMovieModel>()
    val errorMessage = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()

    //show and hide loader
    private fun apiCallFinished() {
        isLoading.postValue(true)
    }
//    private fun duringTheApiCall() {
//        isLoading.postValue(false)
//    }

    fun getAllTrendingMovies() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllTrendingMovies()
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

    fun getAllLatestMovies() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllTLatestMovies()
            response.enqueue(object : Callback<TrendingMovieList> {
                override fun onResponse(call: Call<TrendingMovieList>, response: Response<TrendingMovieList>) {
                    latestMoviesList.postValue(response.body())
                    apiCallFinished()
                }

                override fun onFailure(call: Call<TrendingMovieList>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }

    fun getAllTimeFavoritesMovies() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllTimeFavoriteMovies()
            response.enqueue(object : Callback<TrendingMovieList> {
                override fun onResponse(call: Call<TrendingMovieList>, response: Response<TrendingMovieList>) {
                    allTimeFavoriteMovieList.postValue(response.body())
                    apiCallFinished()
                }
                override fun onFailure(call: Call<TrendingMovieList>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }

    fun getAllPopularMovies() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllPopularMovies()
            response.enqueue(object : Callback<TrendingMovieList> {
                override fun onResponse(call: Call<TrendingMovieList>, response: Response<TrendingMovieList>) {
                    allPopularMovies.postValue(response.body())
                    apiCallFinished()
                }

                override fun onFailure(call: Call<TrendingMovieList>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }

    fun getMovieDetail(movie_id: Int) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMovieDetails(movie_id)
            response.enqueue(object : Callback<MovieDetailModel> {
                override fun onResponse(call: Call<MovieDetailModel>, response: Response<MovieDetailModel>) {
                    getMovieDetail.postValue(response.body())
                    apiCallFinished()
                }

                override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }

    fun getMovieYoutubeVideo(movie_id: Int) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMovieVideo(movie_id)
            response.enqueue(object : Callback<MovieVideo> {
                override fun onResponse(call: Call<MovieVideo>, response: Response<MovieVideo>) {
                    getMovieVideo.postValue(response.body())
                    apiCallFinished()
                }

                override fun onFailure(call: Call<MovieVideo>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }

    fun getMovieCredits(movie_id: Int) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMovieCredits(movie_id)
            response.enqueue(object : Callback<MovieCredits> {
                override fun onResponse(call: Call<MovieCredits>, response: Response<MovieCredits>) {
                    getMovieCredits.postValue(response.body())
                    apiCallFinished()
                }

                override fun onFailure(call: Call<MovieCredits>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }

    fun getSimilarMovies(movie_id: Int) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSimilarMovies(movie_id)
            response.enqueue(object : Callback<TrendingMovieList> {
                override fun onResponse(call: Call<TrendingMovieList>, response: Response<TrendingMovieList>) {
                    getSimilarMovies.postValue(response.body())
                    apiCallFinished()
                }

                override fun onFailure(call: Call<TrendingMovieList>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }

    fun getSearchResult(query: String) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSearchResult(query)
            response.enqueue(object : Callback<SearchMovieModel> {
                override fun onResponse(call: Call<SearchMovieModel>, response: Response<SearchMovieModel>) {
                    getSearchResult.postValue(response.body())
                    apiCallFinished()
                }

                override fun onFailure(call: Call<SearchMovieModel>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }

}


