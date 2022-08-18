package com.satya.movee.model.trendingMovies


import com.google.gson.annotations.SerializedName

data class TrendingMovieList(
    @SerializedName("results")
    val results: List<Result?>?
)