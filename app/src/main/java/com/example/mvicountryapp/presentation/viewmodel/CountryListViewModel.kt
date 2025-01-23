package com.example.mvicountryapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvicountryapp.data.database.GetCountriesUseCase
import com.example.mvicountryapp.presentation.intent.CountryListIntent
import com.example.mvicountryapp.presentation.state.CountryListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    val intents: Channel<CountryListIntent> = Channel(Channel.UNLIMITED)
    private val _state = MutableStateFlow<CountryListState>(CountryListState.Loading)
    val state: StateFlow<CountryListState> = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
                intents.receiveAsFlow().collect { intent ->
                    when (intent) {
                        is CountryListIntent.LoadCountries -> loadCountries(false)
                        is CountryListIntent.RefreshCountries -> loadCountries(true)
                    }
                }
        }
    }

    private suspend fun loadCountries(forceRefresh: Boolean) {
        _state.value = CountryListState.Loading
        try {
            val countries = getCountriesUseCase.execute(forceRefresh)
            _state.value = CountryListState.Success(countries)
        } catch (e: Exception) {
            _state.value = CountryListState.Error(e.message ?: "Unknown error")
        }
    }
}
