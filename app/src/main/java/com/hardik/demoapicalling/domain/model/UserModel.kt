package com.hardik.demoapicalling.domain.model


import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("city")
    val city: String,
    @SerializedName("companyname")
    val companyName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("suite")
    val suite: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("zipcode")
    val zipcode: String
)