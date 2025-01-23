package com.example.mvicountryapp.data.hilt

import com.example.mvicountryapp.data.database.GetCountriesUseCase
import com.example.mvicountryapp.data.database.GetCountryDetailsUseCase
import android.content.Context
import androidx.room.Room
import com.example.mvicountryapp.data.api.RestCountriesApi
import com.example.mvicountryapp.data.database.AppDatabase
import com.example.mvicountryapp.data.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRestCountriesApi(retrofit: Retrofit): RestCountriesApi {
        return retrofit.create(RestCountriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "countries.db").build()
    }

    @Provides
    @Singleton
    fun provideCountryRepository(
        api: RestCountriesApi,
        database: AppDatabase
    ): CountryRepository {
        return CountryRepository(api, database.countryDao())
    }

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(repository: CountryRepository): GetCountriesUseCase {
        return GetCountriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCountryDetailsUseCase(repository: CountryRepository): GetCountryDetailsUseCase {
        return GetCountryDetailsUseCase(repository)
    }
}
