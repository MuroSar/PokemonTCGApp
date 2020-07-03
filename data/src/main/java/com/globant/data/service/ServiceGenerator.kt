package com.globant.data.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {
    private val httpClient = OkHttpClient.Builder().build()

    private val builder = Retrofit.Builder()
        .baseUrl(POKEMON_TCG_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient).build()
        return retrofit.create(serviceClass)
    }

    companion object {
        private const val POKEMON_TCG_BASE_URL = "https://api.pokemontcg.io"
    }
}
