package com.globant.data.mapper

import com.globant.domain.entity.PokemonCard
import com.globant.domain.entity.PokemonCardResponse

class PokemonCardListMapper : BaseMapper<List<PokemonCardResponse>, List<PokemonCard>, MutableMap<String, Int>?> {

    override fun transform(type: List<PokemonCardResponse>, resources: MutableMap<String, Int>?): List<PokemonCard> {

        val pokemonCardReturnList: MutableList<PokemonCard> = mutableListOf()

        type.map {
            pokemonCardReturnList.add(PokemonCard(it.id, it.name, it.imageUrl, it.types?.get(ZERO), it.supertype, it.subtype))
        }
        return pokemonCardReturnList
    }

    companion object {
        private const val ZERO = 0
    }
}
