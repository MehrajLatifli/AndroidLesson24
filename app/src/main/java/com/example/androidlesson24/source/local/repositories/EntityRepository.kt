package com.example.androidlesson24.source.local.repositories

import com.example.androidlesson24.models.entities.MuseumEntity
import com.example.androidlesson24.source.local.LocalDao
import javax.inject.Inject

class EntityRepository @Inject constructor(
    val localDao: LocalDao
){
    fun addMuseumEntity(productEntity: MuseumEntity) = localDao.add(productEntity)

    suspend fun getMuseumEntity() = localDao.getMuseums()
}