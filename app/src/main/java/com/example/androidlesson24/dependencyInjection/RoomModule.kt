package com.example.androidlesson24.dependencyInjection

import android.app.Application
import androidx.room.Room
import com.example.androidlesson24.source.local.LocalDao
import com.example.androidlesson24.source.local.MuseumDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideLocalDatabase(application: Application): MuseumDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            MuseumDatabase::class.java,
            "Museum_db"
        ).build()
    }

    @Provides
    fun provideLocalDao(localDatabase: MuseumDatabase): LocalDao {
        return localDatabase.createDAO()
    }
}