package com.satya.movee.model.trendingTvShows


import com.google.gson.annotations.SerializedName

data class TvSeriesCast(
    @SerializedName("cast")
    val cast: List<Cast?>?
)