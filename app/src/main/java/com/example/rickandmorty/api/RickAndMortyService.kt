package com.example.rickandmorty.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * RickAndMorty API communication setup via Retrofit.
 */

interface RickAndMortyService {
    /**
     * Get characters.
     */
    @GET("character/")
    suspend fun searchCharacters(
        @Query("page") page: Int,
        @Query("name") name: String
    ): CharactersSearchResponse

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        fun create(): RickAndMortyService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RickAndMortyService::class.java)
        }
    }
}