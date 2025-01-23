package com.example.mvicountryapp.data.api


import retrofit2.http.GET

data class CountryResponse(
    val name: Name,
    val capital: List<String>?,
    val flags: Flags,
    val population: Long,
    val region: String
) {
    data class Name(val common: String)
    data class Flags(val png: String)
}

interface RestCountriesApi {
    @GET("all")
    suspend fun getAllCountries(): List<CountryResponse>
}
