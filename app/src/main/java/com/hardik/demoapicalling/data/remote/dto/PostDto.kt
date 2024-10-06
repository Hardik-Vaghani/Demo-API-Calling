package com.hardik.demoapicalling.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.hardik.demoapicalling.domain.model.PostModel

data class PostDto(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)

fun PostDto.toPostModel():PostModel{
    return PostModel(
        id = id,
        userId = userId,
        title = title,
        body = body,
    )
}