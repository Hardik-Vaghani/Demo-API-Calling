package com.hardik.demoapicalling.di

import android.content.Context
import com.hardik.demoapicalling.common.Constants
import com.hardik.demoapicalling.common.Constants.BASE_URL
import com.hardik.demoapicalling.data.remote.api.ApiInterface
import com.hardik.demoapicalling.data.repository.UserRepositoryImpl
import com.hardik.demoapicalling.domain.repository.UserRepository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppModule{
    val apiInterface: ApiInterface
    val userRepository: UserRepository
}

class AppModuleImpl( private val appContext: Context): AppModule{

    override val apiInterface: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(apiInterface)
    }

}