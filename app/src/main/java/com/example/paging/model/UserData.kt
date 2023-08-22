package com.example.paging.model

data class UserData(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
)