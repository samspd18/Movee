package com.satya.movee.Repositories

import com.satya.movee.network.RetrofitService

class MoviesRepositories constructor(private val retrofitService: RetrofitService) {
    //for movies
    fun getAllTrendingMovies() = retrofitService.getAllTrendingMovies()
    fun getAllTLatestMovies() = retrofitService.getAllLatestMovies()
    fun getAllTimeFavoriteMovies() = retrofitService.getAllTimeFavoriteMovies()
    fun getAllPopularMovies() = retrofitService.getAllPopularMovies()
    fun getMovieDetails(movie_id: Int) = retrofitService.getMovieDetail(movie_id)
    fun getMovieVideo(movie_id: Int) = retrofitService.getMovieVideo(movie_id)
    fun getMovieCredits(movie_id: Int) = retrofitService.getMovieCredits(movie_id)
    fun getSimilarMovies(movie_id: Int) = retrofitService.getSimilarMovies(movie_id)
    fun getSearchResult(query: String) = retrofitService.getSearchResult(query)

    //tv shows
    fun getAllTrendingTvShows() = retrofitService.getAllTrendingTvShows()
    fun getTvSearchData(query: String) = retrofitService.getSearchResultTv(query)
    fun getTvShowsAirToday() = retrofitService.getAllAiringTodayShows()
    fun getTopRatedShows() = retrofitService.getTopRatedTvShows()
    fun getPopularTvSeries() = retrofitService.getPopularTvSeries()
    fun getSeriesDetail(tv_id: Int) = retrofitService.getSeriesDetail(tv_id)
}