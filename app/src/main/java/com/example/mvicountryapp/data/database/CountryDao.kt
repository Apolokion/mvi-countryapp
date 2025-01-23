package com.example.mvicountryapp.data.database

import androidx.room.*

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    suspend fun getAllCountries(): List<CountryEntity>

    @Query("SELECT * FROM countries WHERE name = :countryName LIMIT 1")
    suspend fun getCountryByName(countryName: String): CountryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)

    @Query("DELETE FROM countries")
    suspend fun clearAll()
}
