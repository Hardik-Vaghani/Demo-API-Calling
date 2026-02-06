package com.hardik.demoapicalling.domain.repository

import com.hardik.demoapicalling.data.remote.dto.UserDto

interface UserRepository {
    suspend fun getUsers(): List<UserDto>
}