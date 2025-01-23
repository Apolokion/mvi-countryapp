package com.example.mvicountryapp.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvicountryapp.R
import com.example.mvicountryapp.data.model.Country

class CountryAdapter(
    private var countries: List<Country>,
    private val onItemClick: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size

    fun updateData(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvCapital: TextView = itemView.findViewById(R.id.tvCapital)

        fun bind(country: Country) {
            tvName.text = country.name
            tvCapital.text = country.capital
            Glide.with(itemView.context).load(country.flagUrl).into(ivFlag)

            itemView.setOnClickListener {
                onItemClick(country)
            }
        }
    }
}
