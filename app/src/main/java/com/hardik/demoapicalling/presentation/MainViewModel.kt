package com.hardik.demoapicalling.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardik.demoapicalling.common.Resource
import com.hardik.demoapicalling.domain.model.UserModel
import com.hardik.demoapicalling.domain.use_case.GetUserUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val _state = MutableLiveData<UserListState<UserModel>>()
    val state: LiveData<UserListState<UserModel>> = _state

    init {
        getUsers()
    }

    private fun getUsers() {
        getUserUseCase().onEach { result: Resource<List<UserModel>> ->
            when(result){
                is Resource.Success -> { _state.value = UserListState(users = result.data ?: emptyList()) }
                is Resource.Error -> { _state.value = UserListState(error = result.message ?: "An unexpected error occurred") }
                is Resource.Loading -> { _state.value = UserListState(isLoading = true) }
            }

        }.launchIn(viewModelScope)
    }
}

//class MainViewModelFactory: ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MainViewModel() as T
//    }
//}

// use like this or MainViewModelFactoryHelper class