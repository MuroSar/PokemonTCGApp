package com.globant.data.mapper

import com.globant.domain.entity.PokemonSupertype

class PokemonSupertypeMapper : BaseMapper<List<String>, List<PokemonSupertype>, MutableMap<String, Int>?> {

    override fun transform(
        type: List<String>,
        resources: MutableMap<String, Int>?
    ): List<PokemonSupertype> {
        val pokemonSupertypeReturnList: MutableList<PokemonSupertype> = mutableListOf()

        type.map {
            resources?.get(it)?.let { resource ->
                pokemonSupertypeReturnList.add(PokemonSupertype(it, resource))
            }
        }
        return pokemonSupertypeReturnList
    }
}
