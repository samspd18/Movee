package com.satya.movee.network

import com.satya.movee.constants.Constant.Companion.apiKey
import com.satya.movee.model.search.movie.SearchMovieModel
import com.satya.movee.model.trendingMovies.MovieCredits
import com.satya.movee.model.trendingMovies.MovieDetailModel
import com.satya.movee.model.trendingMovies.MovieVideo
import com.satya.movee.model.trendingMovies.TrendingMovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    //trending movies
    @GET("trending/movie/week?api_key=${apiKey}&region=IN")
    fun getAllTrendingMovies(): Call<TrendingMovieList>

    //latest movies
    @GET("movie/now_playing?api_key=${apiKey}&region=IN")
    fun getAllLatestMovies(): Call<TrendingMovieList>

    //all time favorites
    @GET("movie/top_rated?api_key=${apiKey}&language=en-US&page=1&region=IN")
    fun getAllTimeFavoriteMovies(): Call<TrendingMovieList>

    //popular movies
    @GET("movie/popular?api_key=${apiKey}&language=en-US&page=1&region=IN")
    fun getAllPopularMovies(): Call<TrendingMovieList>

    //movie details
    @GET("movie/{movie_id}?api_key=${apiKey}&language=en-US")
    fun getMovieDetail(@Path("movie_id") movie_id: Int): Call<MovieDetailModel>

    //movie video
    @GET("movie/{movie_id}/videos?api_key=${apiKey}&language=en-US")
    fun getMovieVideo(@Path("movie_id") movie_id: Int): Call<MovieVideo>

    //movie credits
    @GET("movie/{movie_id}/credits?api_key=${apiKey}&language=en-US")
    fun getMovieCredits(@Path("movie_id") movie_id: Int): Call<MovieCredits>

    //similar movies
    @GET("movie/{movie_id}/similar?api_key=${apiKey}&language=en-US")
    fun getSimilarMovies(@Path("movie_id") movie_id: Int): Call<TrendingMovieList>

    //search movies
    @GET("search/movie?api_key=${apiKey}&language=en-US&page=1&include_adult=false&region=IN")
    fun getSearchResult(@Query("query") query: String): Call<SearchMovieModel>

}
//fun getNews(@Query("category") category: String): NewsServiceModel