package com.example.mvicountryapp.presentation.state


import com.example.mvicountryapp.data.model.Country

sealed class CountryListState {
    object Loading : CountryListState()
    data class Success(val countries: List<Country>) : CountryListState()
    data class Error(val message: String) : CountryListState()
}
