package com.satya.movee.viewmodel.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satya.movee.Repositories.MoviesRepositories

class PopularViewModel (private val repository: MoviesRepositories): ViewModel()  {

    val errorMessage = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()

    private fun apiCallFinished() {
        isLoading.postValue(true)
    }
}