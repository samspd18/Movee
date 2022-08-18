package com.satya.movee.viewmodel.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.viewmodel.ViewModel.MoviesViewModel
import com.satya.movee.viewmodel.ViewModel.TvShowsViewModel

class MoviesViewModelFactory constructor(private val repository: MoviesRepositories) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            MoviesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

class TvViewModelFactory constructor(private val repository: MoviesRepositories) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TvShowsViewModel::class.java)) {
            TvShowsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}