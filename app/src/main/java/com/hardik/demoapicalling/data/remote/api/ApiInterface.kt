package com.hardik.demoapicalling.data.remote.api

import com.hardik.demoapicalling.data.remote.dto.PostDto
import com.hardik.demoapicalling.data.remote.dto.UserDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiInterface {

    @GET("/users")
    suspend fun getUsers(): List<UserDto>

    /**
     * Without suspend keyword,it is necessary to use Call<Post>,
     * With suspend keyword, It isn't necessary to use Call<Post>, You have two option Call<Post> or Post.
     * */
    //region Example of @Body or @Multipart or @FormUrlEncoded

    @POST("posts")
    suspend fun createPost(@Body post: PostDto): PostDto// Call<PostDto>

    // OR

    @Multipart
    @POST("posts")
    suspend fun createPost(
        @Part("userId") userId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("body") body: RequestBody
    ): PostDto// Call<PostDto>

    // OR

    @FormUrlEncoded
    @POST("posts")
    suspend fun createPost(
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): PostDto// Call<PostDto>

    //endregion


    //region Example of @Multipart or @FormUrlEncoded
    // This is for example only
    /** If you still want to upload an image file along with the description, you should stick with @Multipart. Here’s a reminder of the @Multipart setup:*/
    @Multipart
    @POST("users/{userId}/upload")
    fun uploadImage(
        @Path("userId") userId: Int,
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<ResponseBody>

    // OR

    /**
     * you can use @Field with Retrofit for sending form data, but @Field is typically used with @FormUrlEncoded requests, which don’t support file uploads directly. If you want to upload an image and use fields in the same request, you will still need to use @Multipart.
     *
     * However, if you specifically want to keep using @Field, here's how you could structure your request for scenarios where you don't need to upload files (like images). You would then need to convert your image to Base64 or send it through another means if your API accepts it that way.
     * @suppress Using @Field with @FormUrlEncoded
     * */
    @FormUrlEncoded
    @POST("users/{userId}/upload")
    fun uploadData(
        @Path("userId") userId: Int,
        @Field("description") description: String,
        @Field("image") image: String // Assuming image is Base64 encoded
    ): Call<ResponseBody>

    // if you need to upload files (like images) along with other data (like a description), stick with the @Multipart approach. If you do not need to upload files, you can use @FormUrlEncoded with @Field.
    //endregion


}