package com.example.turkishmuseums.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turkishmuseums.models.responses.get.city.CityData
import com.example.turkishmuseums.models.responses.get.museum.MuseumData
import com.example.turkishmuseums.models.responses.get.region.DistrictData
import com.example.turkishmuseums.source.api.Resource
import com.example.turkishmuseums.source.api.repositories.MuseumRepository
import com.example.turkishmuseums.source.local.mapping.toMuseumEntity
import com.example.turkishmuseums.source.local.repositories.EntityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: MuseumRepository,
    private val repo_entity: EntityRepository
) : ViewModel() {

    private val _cities = MutableLiveData<List<CityData>>()
    val cities: LiveData<List<CityData>> = _cities

    private val _museums = MutableLiveData<List<MuseumData>>()
    val museums: LiveData<List<MuseumData>> = _museums

    private val _districts = MutableLiveData<List<DistrictData>>()
    val districts: LiveData<List<DistrictData>> = _districts

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error


    private var originalCityList = listOf<CityData>()
    private var originalMuseumList = listOf<MuseumData>()
    private var originalRegionList = listOf<DistrictData>()

    init {
        _loading.value = false
        _error.value = null

    }

    fun getAllCities() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getAllCities()
            withContext(Dispatchers.Main) {
                when (response) {
                    is Resource.Success -> {
                        _loading.value = false
                        val cityResponse = response.data
                        if (cityResponse != null) {
                            _cities.value = cityResponse.data
                            originalCityList = cityResponse.data
                        } else {
                            _error.value = "No cities found"
                            _cities.value = emptyList()
                            Log.e("APIFailed",_error.value.toString())

                        }
                    }
                    is Resource.Error -> {
                        _loading.value = false
                        _error.value = "Failed to fetch cities: ${response.message}"
                        Log.e("APIFailed",_error.value.toString())
                    }
                }
            }
        }
    }


    fun getDistricts(city:String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getDistricts(city)
            withContext(Dispatchers.Main) {
                when (response) {
                    is Resource.Success -> {
                        _loading.value = false
                        val itemResponse = response.data
                        if (itemResponse != null) {
                            _districts.value = itemResponse.data
                            originalRegionList = itemResponse.data
                        } else {
                            _error.value = "No regions found"
                            _cities.value = emptyList()
                            Log.e("APIFailed",_error.value.toString())
                        }
                    }
                    is Resource.Error -> {
                        _loading.value = false
                        _error.value = "Failed to fetch regions: ${response.message}"
                        Log.e("APIFailed",_error.value.toString())
                    }
                }
            }
        }
    }


    fun getAllMuseumByCurrentData(city: String, district:String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getAllMuseumByCurrentData(city,district)
            withContext(Dispatchers.Main) {
                when (response) {
                    is Resource.Success -> {
                        _loading.value = false
                        val itemResponse = response.data
                        if (itemResponse != null) {
                            _museums.value = itemResponse.data.orEmpty()
                            originalMuseumList=itemResponse.data.orEmpty()
                           itemResponse.data.forEach { item ->
                               delay(500)
                                withContext(Dispatchers.IO) {
                                    try {
                                        repo_entity.addMuseumEntity(item.toMuseumEntity())
                                    } catch (e: Exception) {
                                        Log.e("DatabaseError", "Error adding museum entity: ${e.message}")
                                    }
                                }
                            }


                        } else {
                            _error.value = "No regions found"
                            _museums.value = emptyList()
                            Log.e("APIFailed",_error.value.toString())
                        }
                    }
                    is Resource.Error -> {
                        _loading.value = false
                        _error.value = "Failed to fetch regions: ${response.message}"
                        Log.e("APIFailed",_error.value.toString())
                    }
                }
            }
        }
    }




    private fun fetchRemoteProducts(city: String, district:String) {
        viewModelScope.launch(Dispatchers.Main) {
            delay(100)
            val response = repo.getAllMuseumByCurrentData(city,district)
            when (response) {
                is Resource.Success -> {
                    _loading.value = false
                    val itemResponse = response.data
                    if (itemResponse != null && itemResponse != null) {
                        _museums.value = itemResponse.data.orEmpty()
                        originalMuseumList=itemResponse.data.orEmpty()
                        itemResponse.data.forEach { item ->
                            withContext(Dispatchers.IO) {
                                repo_entity.addMuseumEntity(item.toMuseumEntity())
                            }
                        }
                    } else {
                        _error.value = "No products found"
                        _museums.value = emptyList()
                    }
                }
                is Resource.Error -> {
                    _error.value = "Failed to fetch products: ${response.message}"
                }
                else -> {
                    _loading.value = false
                }
            }
        }
    }


    fun search(query: String) {

        if (query.isBlank()) {
            _museums.value = originalMuseumList

        }

        val filtered = originalMuseumList.filter { item ->
            item.name?.contains(query, ignoreCase = true) ?: false
        }
        _museums.value = filtered

    }

}