package com.example.turkishmuseums.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Museums")
data class  MuseumEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("museum_id")
    val id: Int,

    val address: String,

    val city: String,

    val description: String,

    val details: String,

    val district: String,

    val email: String,

    val name: String,

    val phone: String,

    val website: String,

    val workingTime: String
)