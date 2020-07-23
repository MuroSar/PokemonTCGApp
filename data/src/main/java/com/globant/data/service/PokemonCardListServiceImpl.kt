package com.globant.data.service

import com.globant.data.mapper.PokemonCardListMapper
import com.globant.data.service.api.PokemonTCGApi
import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardListService
import com.globant.domain.util.Result

class PokemonCardListServiceImpl : PokemonCardListService {

    private val api = ServiceGenerator()
    private val mapper = PokemonCardListMapper()

    override fun getPokemonCardList(pokemonCardGroup: String, pokemonCardGroupSelected: String): Result<List<PokemonCard>> {
        try {
            val data: HashMap<String, String> = hashMapOf()
            data[pokemonCardGroup] = pokemonCardGroupSelected

            val callResponse = api.createService(PokemonTCGApi::class.java).getPokemonCardList(data)
            val response = callResponse.execute()
            if (response.isSuccessful)
                response.body()?.cards?.let {
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
        private const val NOT_FOUND = "Card selection not found"
    }
}
