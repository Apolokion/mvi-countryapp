package com.example.mvicountryapp.data.database


import com.example.mvicountryapp.data.model.Country
import com.example.mvicountryapp.data.repository.CountryRepository

class GetCountriesUseCase(private val repository: CountryRepository) {
    suspend fun execute(forceRefresh: Boolean): List<Country> {
        return repository.getAllCountries(forceRefresh).map {
            Country(
                name = it.name,
                capital = it.capital,
                flagUrl = it.flagUrl,
                population = it.population,
                region = it.region
            )
        }
    }
}

class GetCountryDetailsUseCase(private val repository: CountryRepository) {
    suspend fun execute(name: String): Country? {
        val entity = repository.getCountryDetails(name)
        return entity?.let {
            Country(
                name = it.name,
                capital = it.capital,
                flagUrl = it.flagUrl,
                population = it.population,
                region = it.region
            )
        }
    }
}
