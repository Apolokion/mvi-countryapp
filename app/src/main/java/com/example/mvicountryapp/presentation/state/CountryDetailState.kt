package com.example.mvicountryapp.presentation.state

import com.example.mvicountryapp.data.model.Country

sealed class CountryDetailState {
    object Loading : CountryDetailState()
    data class Success(val country: Country) : CountryDetailState()
    data class Error(val message: String) : CountryDetailState()
}
