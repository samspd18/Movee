package com.satya.movee.viewmodel.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.constants.Constant
import com.satya.movee.model.search.movie.SearchMovieModel
import com.satya.movee.model.search.tv.TvSearchModel
import com.satya.movee.model.trendingMovies.TrendingMovieList
import com.satya.movee.model.trendingTvShows.TrendingTvShowsList
import com.satya.movee.model.trendingTvShows.TvSeriesCast
import com.satya.movee.model.trendingTvShows.TvSeriesDetail
import com.satya.movee.model.trendingTvShows.video.TvSeriesVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowsViewModel (private val repository: MoviesRepositories): ViewModel()  {

    val trendingMovieList = MutableLiveData<TrendingTvShowsList>()
    val tvSeriesAirToday = MutableLiveData<TrendingTvShowsList>()
    val topRatedSeries = MutableLiveData<TrendingTvShowsList>()
    val getPopularSeries = MutableLiveData<TrendingTvShowsList>()
    val getTvSeriesDetail = MutableLiveData<TvSeriesDetail>()
    val getTvSeriesCast = MutableLiveData<TvSeriesCast>()
    val getTvSeriesVide = MutableLiveData<TvSeriesVideo>()
    val getSearchResultTv = MutableLiveData<TvSearchModel>()
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

    fun getTVShowsAirToday() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTvShowsAirToday()
            response.enqueue(object : Callback<TrendingTvShowsList> {
                override fun onResponse(call: Call<TrendingTvShowsList>, response: Response<TrendingTvShowsList>) {
                    apiCallFinished()
                    tvSeriesAirToday.postValue(response.body())
                }
                override fun onFailure(call: Call<TrendingTvShowsList>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getTopRatedShowsInIMDB() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTopRatedShows()
            response.enqueue(object : Callback<TrendingTvShowsList> {
                override fun onResponse(call: Call<TrendingTvShowsList>, response: Response<TrendingTvShowsList>) {
                    apiCallFinished()
                    topRatedSeries.postValue(response.body())
                }
                override fun onFailure(call: Call<TrendingTvShowsList>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getPopularTvSeries() {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPopularTvSeries()
            response.enqueue(object : Callback<TrendingTvShowsList> {
                override fun onResponse(call: Call<TrendingTvShowsList>, response: Response<TrendingTvShowsList>) {
                    apiCallFinished()
                    getPopularSeries.postValue(response.body())
                }
                override fun onFailure(call: Call<TrendingTvShowsList>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getTvSeriesDetail(tv_id: Int) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSeriesDetail(tv_id)
            response.enqueue(object : Callback<TvSeriesDetail> {
                override fun onResponse(call: Call<TvSeriesDetail>, response: Response<TvSeriesDetail>) {
                    apiCallFinished()
                    getTvSeriesDetail.postValue(response.body())
                }
                override fun onFailure(call: Call<TvSeriesDetail>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getTvSeriesCast(tv_id: Int) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSeriesCast(tv_id)
            response.enqueue(object : Callback<TvSeriesCast> {
                override fun onResponse(call: Call<TvSeriesCast>, response: Response<TvSeriesCast>) {
                    apiCallFinished()
                    getTvSeriesCast.postValue(response.body())
                }
                override fun onFailure(call: Call<TvSeriesCast>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getSeriesVideo(tv_id: Int) {
        duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSeriesVideo(tv_id)
            response.enqueue(object : Callback<TvSeriesVideo> {
                override fun onResponse(call: Call<TvSeriesVideo>, response: Response<TvSeriesVideo>) {
                    apiCallFinished()
                    getTvSeriesVide.postValue(response.body())
                }
                override fun onFailure(call: Call<TvSeriesVideo>, t: Throwable) {
                    apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getSearchResult(query: String) {
        Constant.duringTheApiCall()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTvSearchData(query)
            response.enqueue(object : Callback<TvSearchModel> {
                override fun onResponse(call: Call<TvSearchModel>, response: Response<TvSearchModel>) {
                    getSearchResultTv.postValue(response.body())
                    apiCallFinished()
                }

                override fun onFailure(call: Call<TvSearchModel>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    apiCallFinished()
                }
            })
        }
    }
}