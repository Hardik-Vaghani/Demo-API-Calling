package com.hardik.demoapicalling.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.hardik.demoapicalling.domain.model.UserModel

data class UserDto(
    @SerializedName("address")
    val address: Address,
    @SerializedName("company")
    val company: Company,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("website")
    val website: String
) {
    data class Address(
        @SerializedName("city")
        val city: String,
        @SerializedName("geo")
        val geo: Geo,
        @SerializedName("street")
        val street: String,
        @SerializedName("suite")
        val suite: String,
        @SerializedName("zipcode")
        val zipcode: String
    ) {
        data class Geo(
            @SerializedName("lat")
            val lat: String,
            @SerializedName("lng")
            val lng: String
        )
    }

    data class Company(
        @SerializedName("bs")
        val bs: String,
        @SerializedName("catchPhrase")
        val catchPhrase: String,
        @SerializedName("name")
        val name: String
    )
}


fun UserDto.toUserModel():UserModel{
    return UserModel(
        city = address.city,
        companyName = company.name,
        email= email,
        id = id,
        lat = address.geo.lat,
        lng = address.geo.lng,
        name = name,
        phone = phone,
        street = address.street,
        suite = address.suite,
        username = username,
        website = website,
        zipcode = address.zipcode,
    )
}