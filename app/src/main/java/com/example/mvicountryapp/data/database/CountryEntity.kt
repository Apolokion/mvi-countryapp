package com.example.mvicountryapp.data.database


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey val name: String,
    val capital: String,
    val flagUrl: String,
    val population: Long,
    val region: String
)
