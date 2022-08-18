package com.satya.movee.model.trendingMovies


import com.google.gson.annotations.SerializedName

data class MovieVideo(
    @SerializedName("results")
    val results: List<ResultX>?
)