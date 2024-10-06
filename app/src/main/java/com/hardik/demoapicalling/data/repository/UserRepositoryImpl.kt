package com.hardik.demoapicalling.data.repository

import com.hardik.demoapicalling.data.remote.api.ApiInterface
import com.hardik.demoapicalling.data.remote.dto.PostDto
import com.hardik.demoapicalling.data.remote.dto.UserDto
import com.hardik.demoapicalling.domain.model.PostModel
import com.hardik.demoapicalling.domain.model.toPostDto
import com.hardik.demoapicalling.domain.repository.UserRepository

class UserRepositoryImpl (private val apiInterface: ApiInterface):UserRepository{

    override suspend fun getUsers(): List<UserDto> {
        return apiInterface.getUsers()
    }

    override suspend fun createPost(post: PostModel): PostDto {
        return apiInterface.createPost(post = post.toPostDto())
    }

}