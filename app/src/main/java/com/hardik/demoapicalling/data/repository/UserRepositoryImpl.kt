package com.hardik.demoapicalling.data.repository

import com.hardik.demoapicalling.data.remote.api.ApiInterface
import com.hardik.demoapicalling.data.remote.dto.UserDto
import com.hardik.demoapicalling.domain.repository.UserRepository

//class UserRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface):UserRepository{
class UserRepositoryImpl (private val apiInterface: ApiInterface):UserRepository{

    override suspend fun getUsers(): List<UserDto> {
        return apiInterface.getUsers()
    }

}