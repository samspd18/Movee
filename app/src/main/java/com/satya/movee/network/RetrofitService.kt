package com.satya.movee.network

import com.satya.movee.constants.Constant.Companion.apiKey
import com.satya.movee.model.popular.PopularPersonModel
import com.satya.movee.model.search.movie.SearchMovieModel
import com.satya.movee.model.search.tv.TvSearchModel
import com.satya.movee.model.trendingMovies.MovieCredits
import com.satya.movee.model.trendingMovies.MovieDetailModel
import com.satya.movee.model.trendingMovies.MovieVideo
import com.satya.movee.model.trendingMovies.TrendingMovieList
import com.satya.movee.model.trendingTvShows.TrendingTvShowsList
import com.satya.movee.model.trendingTvShows.TvSeriesCast
import com.satya.movee.model.trendingTvShows.TvSeriesDetail
import com.satya.movee.model.trendingTvShows.video.TvSeriesVideo
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

    //trending tv
    @GET("trending/tv/week?api_key=${apiKey}&region=IN")
    fun getAllTrendingTvShows(): Call<TrendingTvShowsList>

    //search tv
    @GET("search/tv?api_key=${apiKey}&language=en-US&page=1&include_adult=false&region=IN")
    fun getSearchResultTv(@Query("query") query: String): Call<TvSearchModel>

    //tv shows airing today
    @GET("tv/airing_today?api_key=${apiKey}&language=en-US&page=1")
    fun getAllAiringTodayShows(): Call<TrendingTvShowsList>

    //top rated tv shows
    @GET("tv/top_rated?api_key=${apiKey}&language=en-US&page=1")
    fun getTopRatedTvShows(): Call<TrendingTvShowsList>

    //popular tv series
    @GET("tv/popular?api_key=${apiKey}&language=en-US&page=1")
    fun getPopularTvSeries(): Call<TrendingTvShowsList>

    //get series detail
    @GET("tv/{tv_id}?api_key=${apiKey}&language=en-US")
    fun getSeriesDetail(@Path("tv_id") tv_id: Int): Call<TvSeriesDetail>

    //get tv shows credit
    @GET("tv/{tv_id}/credits?api_key=${apiKey}&language=en-US")
    fun getSeriesCast(@Path("tv_id") tv_id: Int): Call<TvSeriesCast>

    //get tv series video
    @GET("tv/{tv_id}/videos?api_key=${apiKey}&language=en-US")
    fun getSeriesVideo(@Path("tv_id") tv_id: Int): Call<TvSeriesVideo>

    //popular person
    @GET("person/popular?api_key=${apiKey}&language=en-US")
    fun getPopularPerson(@Query("page") page: Int): Call<PopularPersonModel>

    @GET("movie/upcoming?api_key=${apiKey}&region=IN")
    fun getUpcomingMovies(): Call<TrendingMovieList>
}