package com.example.turkishmuseums.source.local.mapping

import com.example.turkishmuseums.models.entities.MuseumEntity
import com.example.turkishmuseums.models.responses.get.museum.MuseumData

fun MuseumEntity.toMuseum(): MuseumData {
    return MuseumData(
        id = this.id,
        name = this.name,
        description = this.description,
        address = this.address,
        workingTime = this.workingTime,
        details = this.details,
        phone = this.phone,
        email = this.email,
        website = this.website,
        city = this.city,
        district = this.district
    )
}

fun MuseumData.toMuseumEntity(): MuseumEntity {
    return MuseumEntity(

        id = this.id,
        name = this.name,
        description = this.description,
        address = this.address,
        workingTime = this.workingTime,
        details = this.details,
        phone = this.phone,
        email = this.email,
        website = this.website,
        city = this.city,
        district = this.district
    )
}