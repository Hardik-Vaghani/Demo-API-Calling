package com.hardik.demoapicalling.data.remote.api

import com.hardik.demoapicalling.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("/users")
    suspend fun getUsers(): List<UserDto>
}