package com.example.turkishmuseums.source.local.repositories

import com.example.turkishmuseums.models.entities.MuseumEntity
import com.example.turkishmuseums.source.local.LocalDao
import javax.inject.Inject

class EntityRepository @Inject constructor(
    val localDao: LocalDao
){
    fun addMuseumEntity(productEntity: MuseumEntity) = localDao.add(productEntity)

    suspend fun getMuseumEntity() = localDao.getMuseums()
}