package com.globant.data.service.api

import com.globant.data.service.response.PokemonTypesBaseResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokemonTCGApi {
    @GET("v1/types")
    fun getPokemonTypes(): Call<PokemonTypesBaseResponse<List<String>>>
}
