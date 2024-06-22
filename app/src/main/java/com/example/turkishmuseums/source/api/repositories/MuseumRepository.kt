package com.example.turkishmuseums.source.api.repositories

import android.util.Log
import com.example.turkishmuseums.models.responses.get.city.CityResponse
import com.example.turkishmuseums.models.responses.get.museum.MuseumResponse
import com.example.turkishmuseums.models.responses.get.region.DistrictResponse
import com.example.turkishmuseums.source.api.IApiManager
import com.example.turkishmuseums.source.api.Resource
import com.example.turkishmuseums.utilities.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MuseumRepository  @Inject constructor(private val api: IApiManager) {

    suspend fun getAllCities(): Resource<CityResponse> {
        return safeApiRequest {
            api.getAllCities(/*API_KEY*,*/)
        }
    }


    suspend fun getDistricts(city:String): Resource<DistrictResponse> {
        return safeApiRequest {
            api.getDistricts(/*API_KEY*,*/city)
        }
    }

    suspend fun getAllMuseumByCurrentData(city:String, district:String): Resource<MuseumResponse> {
        return safeApiRequest {
            api.getAllMuseumByCurrentData(/*API_KEY*,*/city,district)
        }
    }



    suspend fun <T> safeApiRequest(request: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {

                val response = request.invoke()

                if (response.isSuccessful) {
                    Resource.Success(response.body())
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Log.e("APIFailed",e.message.toString())
                Resource.Error(e.localizedMessage)
            }
        }
    }
}