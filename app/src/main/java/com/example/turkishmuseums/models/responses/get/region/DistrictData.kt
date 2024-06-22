package com.example.turkishmuseums.models.responses.get.region


import com.google.gson.annotations.SerializedName

data class DistrictData(
    @SerializedName("cities")
    val cities: String,
    @SerializedName("slug")
    val slug: String
)