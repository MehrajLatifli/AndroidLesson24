package com.example.turkishmuseums.models.responses.get.museum


import com.google.gson.annotations.SerializedName

data class MuseumResponse(
    @SerializedName("creditUsed")
    val creditUsed: Int,
    @SerializedName("data")
    val `data`: List<MuseumData>,
    @SerializedName("endpoint")
    val endpoint: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("messageTR")
    val messageTR: String,
    @SerializedName("rowCount")
    val rowCount: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("systemTime")
    val systemTime: Int
)