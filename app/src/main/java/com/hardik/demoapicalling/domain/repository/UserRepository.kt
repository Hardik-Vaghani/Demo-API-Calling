package com.hardik.demoapicalling.domain.repository

import com.hardik.demoapicalling.data.remote.dto.PostDto
import com.hardik.demoapicalling.data.remote.dto.UserDto
import com.hardik.demoapicalling.domain.model.PostModel

interface UserRepository {
    suspend fun getUsers(): List<UserDto>
    suspend fun createPost(post: PostModel): PostDto
}