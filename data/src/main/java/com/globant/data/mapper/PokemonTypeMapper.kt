package com.globant.data.mapper

import com.globant.domain.entity.PokemonType

class PokemonTypeMapper : BaseMapper<List<String>, MutableMap<String, Pair<Int, Int>>> {

    override fun transformPokemonTypesList(
        pokemonTypeResponse: List<String>,
        pokemonTypesResources: MutableMap<String, Pair<Int, Int>>
    ): List<PokemonType> {
        val pokemonTypeReturnList: MutableList<PokemonType> = mutableListOf()

        pokemonTypeResponse.map {
            pokemonTypesResources[it]?.let { pairResources ->
                pokemonTypeReturnList.add(PokemonType(it, pairResources.first, pairResources.second))
            }
        }
        return pokemonTypeReturnList
    }
}
