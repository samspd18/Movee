package com.satya.movee.model.search.movie


import com.google.gson.annotations.SerializedName

data class SearchMovieModel(
    @SerializedName("results")
    val results: List<Result?>?
)