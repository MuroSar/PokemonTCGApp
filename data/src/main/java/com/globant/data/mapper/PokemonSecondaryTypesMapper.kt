package com.globant.data.mapper

import com.globant.domain.entity.SecondaryTypes

class PokemonSecondaryTypesMapper : BaseMapper<List<String>, List<SecondaryTypes>, MutableMap<String, Int>?> {

    override fun transform(
        type: List<String>,
        resources: MutableMap<String, Int>?
    ): List<SecondaryTypes> {
        val pokemonSecondaryTypeReturnList: MutableList<SecondaryTypes> = mutableListOf()

        type.map {
            resources?.get(it)?.let { resource ->
                pokemonSecondaryTypeReturnList.add(SecondaryTypes(it, resource))
            }
        }
        return pokemonSecondaryTypeReturnList
    }
}
