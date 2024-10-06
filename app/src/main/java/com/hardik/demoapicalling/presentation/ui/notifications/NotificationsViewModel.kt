package com.hardik.demoapicalling.presentation.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardik.demoapicalling.common.Resource
import com.hardik.demoapicalling.domain.model.PostModel
import com.hardik.demoapicalling.domain.use_case.CreatePostUseCase
import com.hardik.demoapicalling.presentation.CreatePostState
import kotlinx.coroutines.launch

class NotificationsViewModel(private val createPostUseCase: CreatePostUseCase) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    //region Create Post
    private val _state = MutableLiveData<CreatePostState<PostModel>>()
    val state: LiveData<CreatePostState<PostModel>> = _state

    public fun createPost(post: PostModel){
        _state.value = CreatePostState(isLoading = true)
        viewModelScope.launch {
            createPostUseCase(post).collect{ result: Resource<PostModel> ->
                when(result){
                    is Resource.Success ->{ _state.value = CreatePostState(post = result.data) }
                    is Resource.Error ->{ _state.value = CreatePostState(error = result.message ?: "An unexpected error occurred") }
                    is Resource.Loading ->{ _state.value = CreatePostState(isLoading = true) }
                }
            }
        }
    }
    //endregion

}