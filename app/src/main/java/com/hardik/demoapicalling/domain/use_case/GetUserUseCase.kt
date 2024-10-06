package com.hardik.demoapicalling.domain.use_case

import com.hardik.demoapicalling.common.Resource
import com.hardik.demoapicalling.data.remote.dto.toUserModel
import com.hardik.demoapicalling.di.AppModule
import com.hardik.demoapicalling.domain.model.UserModel
import com.hardik.demoapicalling.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetUserUseCase (/*private val appModule: AppModule*/private val repository: UserRepository ) {
    operator fun invoke(): Flow<Resource<List<UserModel>>> = flow {
        try {
            emit(Resource.Loading<List<UserModel>>())
            val users = repository.getUsers().map { it.toUserModel() }// the data (UserDto to UserModel) transfer here
//            val users = appModule.userRepository.getUsers().map { it.toUserModel() }// the data (UserDto to UserModel) transfer here
            emit(Resource.Success<List<UserModel>>(users))
        } catch(e: HttpException) {
            emit(Resource.Error<List<UserModel>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: java.io.IOException) {
            emit(Resource.Error<List<UserModel>>("Couldn't reach server. Check your internet connection."))
        }
    }
}