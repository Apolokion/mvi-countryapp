package com.example.mvicountryapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvicountryapp.data.database.GetCountryDetailsUseCase
import com.example.mvicountryapp.presentation.intent.CountryDetailIntent
import com.example.mvicountryapp.presentation.state.CountryDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val getCountryDetailsUseCase: GetCountryDetailsUseCase
) : ViewModel() {

    val intents: Channel<CountryDetailIntent> = Channel(Channel.UNLIMITED)
    private val _state = MutableStateFlow<CountryDetailState>(CountryDetailState.Loading)
    val state: StateFlow<CountryDetailState> = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.receiveAsFlow().collect { intent ->
                when (intent) {
                    is CountryDetailIntent.LoadCountryDetails -> loadCountryDetails(intent.countryName)
                }
            }

        }
    }

    private suspend fun loadCountryDetails(countryName: String) {
        _state.value = CountryDetailState.Loading
        try {
            val country = getCountryDetailsUseCase.execute(countryName)
            if (country != null) {
                _state.value = CountryDetailState.Success(country)
            } else {
                _state.value = CountryDetailState.Error("Country not found")
            }
        } catch (e: Exception) {
            _state.value = CountryDetailState.Error(e.message ?: "Unknown error")
        }
    }
}
