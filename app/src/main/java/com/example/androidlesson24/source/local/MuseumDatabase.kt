package com.example.androidlesson24.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidlesson24.models.entities.MuseumEntity

@Database(entities = [MuseumEntity::class], version = 2)
abstract class MuseumDatabase : RoomDatabase() {

    abstract fun createDAO(): LocalDao
}