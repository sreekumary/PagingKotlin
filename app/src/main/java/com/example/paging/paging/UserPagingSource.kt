package com.example.paging.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging.api.RetrofitService
import com.example.paging.model.User
import java.lang.Exception

class UserPagingSource(private val apiService: RetrofitService): PagingSource<Int, User>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {

        return try {
            val position = params.key ?: 1
            val response = apiService.getUser(10,position)
            LoadResult.Page(data = response.body()!!.users, prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}