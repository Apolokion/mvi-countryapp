package com.example.mvicountryapp.presentation.intent


sealed class CountryDetailIntent {
    data class LoadCountryDetails(val countryName: String) : CountryDetailIntent()
}
