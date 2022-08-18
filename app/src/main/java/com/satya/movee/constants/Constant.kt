package com.satya.movee.constants

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.satya.movee.databinding.MovieRvLayoutBinding

class Constant {
    companion object {
        var isLoading = MutableLiveData<Boolean>()

        const val baseUrl = "https://api.themoviedb.org/3/"
        const val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"
        const val apiKey = "52a40c7199101f6f32e27fdd02c5f7dc"
        const val youtubeVideoUrl = "https://www.youtube.com/watch?v="

        fun apiCallFinished() {
            isLoading.postValue(true)
        }
        fun duringTheApiCall() {
            isLoading.postValue(false)
        }
    }
}