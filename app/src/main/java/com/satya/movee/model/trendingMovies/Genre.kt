package com.satya.movee.model.trendingMovies


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    val name: String?
)