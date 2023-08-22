package com.example.paging.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData

import com.example.paging.api.RetrofitService
import com.example.paging.model.User
import com.example.paging.paging.UserPagingSource

class UserRepository  constructor(private val retrofitService: RetrofitService) {

    fun getAllDatas(): LiveData<PagingData<User>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                UserPagingSource(retrofitService)
            }
            , initialKey = 1
        ).liveData
    }

}

