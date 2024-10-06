package com.hardik.demoapicalling.domain.model

import com.hardik.demoapicalling.data.remote.dto.PostDto

data class PostModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)

fun PostModel.toPostDto():PostDto{
    return PostDto(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}