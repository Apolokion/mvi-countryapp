package com.example.mvicountryapp.presentation.intent


sealed class CountryListIntent {
    object LoadCountries : CountryListIntent()
    object RefreshCountries : CountryListIntent()
}
