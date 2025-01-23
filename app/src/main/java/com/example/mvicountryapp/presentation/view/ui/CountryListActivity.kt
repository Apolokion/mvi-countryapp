package com.example.mvicountryapp.presentation.view.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvicountryapp.data.model.Country
import com.example.mvicountryapp.databinding.ActivityCountryListBinding
import com.example.mvicountryapp.presentation.intent.CountryListIntent
import com.example.mvicountryapp.presentation.state.CountryListState
import com.example.mvicountryapp.presentation.view.adapters.CountryAdapter
import com.example.mvicountryapp.presentation.viewmodel.CountryListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryListBinding
    private val viewModel: CountryListViewModel by viewModels()
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CountryAdapter(emptyList()) { country -> onCountryClicked(country) }
        binding.rvCountries.layoutManager = LinearLayoutManager(this)
        binding.rvCountries.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            sendIntent(CountryListIntent.RefreshCountries)
        }

        observeState()
        sendIntent(CountryListIntent.LoadCountries)
    }

    private fun sendIntent(intent: CountryListIntent) {
        lifecycleScope.launch {
            viewModel.intents.send(intent)
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is CountryListState.Loading -> binding.swipeRefresh.isRefreshing = true
                    is CountryListState.Success -> {
                        binding.swipeRefresh.isRefreshing = false
                        adapter.updateData(state.countries)
                    }

                    is CountryListState.Error -> {
                        binding.swipeRefresh.isRefreshing = false
                        Toast.makeText(this@CountryListActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun onCountryClicked(country: Country) {
        val intent = Intent(this, CountryDetailActivity::class.java).apply {
            putExtra("countryName", country.name)
        }
        startActivity(intent)
    }

}
