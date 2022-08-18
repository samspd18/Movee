package com.satya.movee.model.trendingMovies


import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("cast")
    val cast: List<Cast?>?
)