package com.hardik.demoapicalling.domain.use_case

import com.hardik.demoapicalling.common.Resource
import com.hardik.demoapicalling.data.remote.dto.PostDto
import com.hardik.demoapicalling.data.remote.dto.toPostModel
import com.hardik.demoapicalling.data.remote.dto.toUserModel
import com.hardik.demoapicalling.di.AppModule
import com.hardik.demoapicalling.domain.model.PostModel
import com.hardik.demoapicalling.domain.model.UserModel
import com.hardik.demoapicalling.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CreatePostUseCase (/*private val appModule: AppModule*/private val repository: UserRepository ) {
    operator fun invoke(post: PostModel): Flow<Resource<PostModel>> = flow {
        try {
            emit(Resource.Loading<PostModel>())
            val createPost = repository.createPost(post = post).toPostModel() // the data (PostDto to PostModel) transfer here
//            val users = appModule.userRepository.createUser().map { it.toPostModel() }// the data (PostDto to PostModel) transfer here
            emit(Resource.Success<PostModel>(createPost))
        } catch(e: HttpException) {
            emit(Resource.Error<PostModel>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: java.io.IOException) {
            emit(Resource.Error<PostModel>("Couldn't reach server. Check your internet connection."))
        }
    }
}