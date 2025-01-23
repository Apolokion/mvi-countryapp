package com.example.mvicountryapp.data.repository


import com.example.mvicountryapp.data.api.RestCountriesApi
import com.example.mvicountryapp.data.database.CountryDao
import com.example.mvicountryapp.data.database.CountryEntity

class CountryRepository(
    private val api: RestCountriesApi,
    private val dao: CountryDao
) {
    suspend fun getAllCountries(forceRefresh: Boolean): List<CountryEntity> {
        if (forceRefresh || dao.getAllCountries().isEmpty()) {
            val apiResponse = api.getAllCountries()
            val entities = apiResponse.map {
                CountryEntity(
                    name = it.name.common,
                    capital = it.capital?.firstOrNull() ?: "N/A",
                    flagUrl = it.flags.png,
                    population = it.population,
                    region = it.region
                )
            }
            dao.clearAll()
            dao.insertAll(entities)
        }
        return dao.getAllCountries()
    }

    suspend fun getCountryDetails(name: String): CountryEntity? {
        return dao.getCountryByName(name)
    }
}
