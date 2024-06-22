package com.example.turkishmuseums.source.api

import com.example.turkishmuseums.models.responses.get.city.CityResponse
import com.example.turkishmuseums.models.responses.get.museum.MuseumResponse
import com.example.turkishmuseums.models.responses.get.region.DistrictResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IApiManager {


    @Headers( "Content-Type: application/json;charset=UTF-8")

    @GET("museum/cities")
    suspend fun getAllCities(
       @Query("apiKey") apiKey: String
    ): Response<CityResponse>

    @GET("museum/cities")
    suspend fun getDistricts(
        @Query("apiKey") apiKey: String,
        @Query("city") city: String
    ): Response<DistrictResponse>

    @GET("museum/")
    suspend fun getAllMuseumByCurrentData(
       @Query("apiKey") apiKey: String,
        @Query("city") city: String,
        @Query("district") district: String
    ): Response<MuseumResponse>
}