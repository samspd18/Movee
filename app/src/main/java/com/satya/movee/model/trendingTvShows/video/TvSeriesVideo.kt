package com.satya.movee.model.trendingTvShows.video


import com.google.gson.annotations.SerializedName

data class TvSeriesVideo(
    @SerializedName("results")
    val results: List<Result?>?
)