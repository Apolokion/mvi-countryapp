package com.example.mvicountryapp.presentation.view.ui


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mvicountryapp.databinding.ActivityCountryDetailBinding
import com.example.mvicountryapp.presentation.intent.CountryDetailIntent
import com.example.mvicountryapp.presentation.state.CountryDetailState
import com.example.mvicountryapp.presentation.viewmodel.CountryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryDetailBinding
    private val viewModel: CountryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countryName = intent.getStringExtra("countryName") ?: return

        observeState()
        sendIntent(CountryDetailIntent.LoadCountryDetails(countryName))
    }

    private fun sendIntent(intent: CountryDetailIntent) {
        lifecycleScope.launch {
            viewModel.intents.send(intent)
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is CountryDetailState.Loading -> {
                        // loading indicator
                    }

                    is CountryDetailState.Success -> {
                        val country = state.country
                        binding.tvName.text = country.name
                        binding.tvCapital.text = country.capital
                        binding.tvPopulation.text = "Population: ${country.population}"
                        binding.tvRegion.text = "Region: ${country.region}"
                        Glide.with(this@CountryDetailActivity)
                            .load(country.flagUrl)
                            .into(binding.ivFlag)
                    }

                    is CountryDetailState.Error -> {
                        Toast.makeText(this@CountryDetailActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }
}
