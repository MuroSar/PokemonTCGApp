package com.globant.data.service.api

import com.globant.data.service.response.PokemonSupertypesResponse
import com.globant.data.service.response.PokemonTypesResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokemonTCGApi {
    @GET("v1/types")
    fun getPokemonTypes(): Call<PokemonTypesResponse>

    @GET("v1/supertypes")
    fun getPokemonSupertypes(): Call<PokemonSupertypesResponse>
}
