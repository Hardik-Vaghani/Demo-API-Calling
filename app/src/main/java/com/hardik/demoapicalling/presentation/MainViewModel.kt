package com.hardik.demoapicalling.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hardik.demoapicalling.common.Resource
import com.hardik.demoapicalling.domain.use_case.GetUserUseCase
import com.hardik.demoapicalling.presentation.ui.UserListState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//@HiltViewModel
//class ainViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {
//    private val _state = mutableStateOf(UserListState())
//    val state: State<UserListState> = _state
class MainViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val _state = MutableLiveData<UserListState>()
    val state: LiveData<UserListState> = _state

    init {
        getUsers()
    }

    private fun getUsers() {
        getUserUseCase().onEach { result ->
            when(result){
               is Resource.Success -> {_state.value = UserListState(users = result.data ?: emptyList())
               }
               is Resource.Error -> {_state.value = UserListState(error = result.message ?: "An unexpected error occurred")
               }
               is Resource.Loading -> {
                   _state.value = UserListState(isLoading = true)
               }
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
