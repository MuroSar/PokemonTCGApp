package com.globant.data.service.api

import com.globant.data.service.response.PokemonCardListResponse
import com.globant.data.service.response.PokemonSubtypesResponse
import com.globant.data.service.response.PokemonSupertypesResponse
import com.globant.data.service.response.PokemonTypesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PokemonTCGApi {
    @GET("v1/types")
    fun getPokemonTypes(): Call<PokemonTypesResponse>

    @GET("v1/supertypes")
    fun getPokemonSupertypes(): Call<PokemonSupertypesResponse>

    @GET("v1/subtypes")
    fun getPokemonSubtypes(): Call<PokemonSubtypesResponse>

    @GET("v1/cards")
    fun getPokemonCardList(@QueryMap group: Map<String, String>): Call<PokemonCardListResponse>
}
