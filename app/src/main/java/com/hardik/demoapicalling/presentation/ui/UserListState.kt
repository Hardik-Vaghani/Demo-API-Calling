package com.hardik.demoapicalling.presentation.ui

import com.hardik.demoapicalling.domain.model.UserModel

data class UserListState(
    val isLoading: Boolean = false,
    val users: List<UserModel> = emptyList(),
    val error: String = ""
)