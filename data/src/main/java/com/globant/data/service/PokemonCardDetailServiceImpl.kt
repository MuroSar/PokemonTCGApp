package com.globant.data.service

import com.globant.data.mapper.PokemonCardDetailsMapper
import com.globant.data.service.api.PokemonTCGApi
import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardDetailService
import com.globant.domain.util.Result

class PokemonCardDetailServiceImpl : PokemonCardDetailService {
    private val api = ServiceGenerator()
    private val mapper = PokemonCardDetailsMapper()

    override fun getPokemonCardDetail(pokemonCardId: String): Result<PokemonCard> {
        try {
            val callResponse = api.createService(PokemonTCGApi::class.java).getPokemonCardDetail(pokemonCardId)
            val response = callResponse.execute()
            if (response.isSuccessful)
                response.body()?.card?.let {
                    mapper.transform(it)
                }?.let {
                    return Result.Success(it)
                }
        } catch (e: Exception) {
            return Result.Failure(Exception(NOT_FOUND))
        }
        return Result.Failure(Exception(NOT_FOUND))
    }

    companion object {
        private const val NOT_FOUND = "Pokemon card not found"
    }
}
