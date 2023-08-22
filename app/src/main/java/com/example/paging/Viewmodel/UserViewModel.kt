package com.example.paging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging.model.User
import com.example.paging.repository.UserRepository

class UserViewModel constructor(private val mainRepository: UserRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()

    fun getMovieList(): LiveData<PagingData<User>> {
        return mainRepository.getAllDatas().cachedIn(viewModelScope)
    }

}