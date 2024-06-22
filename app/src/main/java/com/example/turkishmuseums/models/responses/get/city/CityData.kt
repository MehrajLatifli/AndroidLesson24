package com.example.turkishmuseums.models.responses.get.city


import com.google.gson.annotations.SerializedName

data class CityData(
    @SerializedName("cities")
    val cities: String,
    @SerializedName("slug")
    val slug: String
)