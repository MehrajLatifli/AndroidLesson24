package com.example.turkishmuseums.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turkishmuseums.models.entities.MuseumEntity

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun add(productEntity: MuseumEntity)

    @Query("SELECT * FROM museums")
    suspend fun getMuseums(): List<MuseumEntity>

}