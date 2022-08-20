package com.satya.movee.model.search.tv


import com.google.gson.annotations.SerializedName

data class TvSearchModel(
    @SerializedName("results")
    val results: List<Result?>?
)