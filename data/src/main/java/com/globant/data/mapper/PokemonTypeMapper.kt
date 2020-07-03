package com.globant.data.mapper

import com.globant.domain.entity.PokemonType

class PokemonTypeMapper : BaseMapper<List<String>, List<PokemonType>> {

    override fun transform(
        type: List<String>,
        resourcesMap: MutableMap<String, Pair<Int, Int>>?
    ): List<PokemonType> {
        val pokemonTypeReturnList: MutableList<PokemonType> = mutableListOf()

        type.map {
            resourcesMap?.get(it)?.let { pairResources ->
                pokemonTypeReturnList.add(PokemonType(it, pairResources.first, pairResources.second))
            }
        }
        return pokemonTypeReturnList
    }
}
