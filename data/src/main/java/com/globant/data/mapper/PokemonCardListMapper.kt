package com.globant.data.mapper

import com.globant.data.service.response.PokemonCardResponse
import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.EMPTY_STRING

class PokemonCardListMapper : BaseMapper<List<PokemonCardResponse>, List<PokemonCard>, MutableMap<String, Int>?> {

    override fun transform(type: List<PokemonCardResponse>, resources: MutableMap<String, Int>?): List<PokemonCard> {

        val pokemonCardReturnList: MutableList<PokemonCard> = mutableListOf()

        type.map {
            pokemonCardReturnList.add(
                PokemonCard(
                    it.id,
                    it.name,
                    it.imageUrl,
                    it.types?.get(TYPE_VALUE) ?: EMPTY_STRING,
                    it.supertype,
                    it.subtype,
                    DetailsResponseMapper().transformToDetails(it)
                )
            )
        }
        return pokemonCardReturnList
    }

    companion object {
        private const val TYPE_VALUE = 0
    }
}
