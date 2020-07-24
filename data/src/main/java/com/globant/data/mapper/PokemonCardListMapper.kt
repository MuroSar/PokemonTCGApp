package com.globant.data.mapper

import com.globant.domain.entity.PokemonCard
import com.globant.domain.entity.PokemonCardResponse
import com.globant.domain.util.NumberConstants.ZERO_INT

class PokemonCardListMapper : BaseMapper<List<PokemonCardResponse>, List<PokemonCard>, MutableMap<String, Int>?> {

    override fun transform(type: List<PokemonCardResponse>, resources: MutableMap<String, Int>?): List<PokemonCard> {

        val pokemonCardReturnList: MutableList<PokemonCard> = mutableListOf()

        type.map {
            pokemonCardReturnList.add(PokemonCard(it.id, it.name, it.imageUrl, it.types?.get(ZERO_INT), it.supertype, it.subtype))
        }
        return pokemonCardReturnList
    }
}
