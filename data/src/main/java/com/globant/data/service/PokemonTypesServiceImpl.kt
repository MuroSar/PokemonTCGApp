package com.globant.data.service

import com.globant.data.mapper.PokemonTypeMapper
import com.globant.data.service.api.PokemonTCGApi
import com.globant.domain.entity.PokemonType
import com.globant.domain.service.PokemonTypesService
import com.globant.domain.util.Result

class PokemonTypesServiceImpl : PokemonTypesService {
    private val api = ServiceGenerator()
    private val mapper = PokemonTypeMapper()

    override fun getPokemonTypesFromAPI(pokemonTypesResources: MutableMap<String, Pair<Int, Int>>): Result<List<PokemonType>> {
        try {
            val callResponse = api.createService(PokemonTCGApi::class.java).getPokemonTypes()
            val response = callResponse.execute()
            if (response.isSuccessful)
                response.body()?.types?.let {
                    mapper.transform(it, pokemonTypesResources)
                }?.let {
                    return Result.Success(it)
                }
        } catch (e: Exception) {
            return Result.Failure(Exception(NOT_FOUND))
        }
        return Result.Failure(Exception(NOT_FOUND))
    }

    companion object {
        private const val NOT_FOUND = "Pokemon types not found"
    }
}
