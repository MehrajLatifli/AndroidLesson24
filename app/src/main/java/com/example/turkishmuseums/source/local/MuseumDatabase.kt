package com.example.turkishmuseums.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.turkishmuseums.models.entities.MuseumEntity

@Database(entities = [MuseumEntity::class], version = 2)
abstract class MuseumDatabase : RoomDatabase() {

    abstract fun createDAO(): LocalDao
}