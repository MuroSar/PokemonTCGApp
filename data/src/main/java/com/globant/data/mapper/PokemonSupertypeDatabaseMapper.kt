package com.globant.data.mapper

import com.globant.data.database.entity.PokemonSupertypeDatabaseEntity
import com.globant.domain.entity.SecondaryTypes

class PokemonSupertypeDatabaseMapper :
    BaseMapper<List<PokemonSupertypeDatabaseEntity>, MutableList<SecondaryTypes>, MutableMap<String, Int>?> {
    override fun transform(
        type: List<PokemonSupertypeDatabaseEntity>,
        resources: MutableMap<String, Int>?
    ): MutableList<SecondaryTypes> {
        val pokemonSecondaryTypesDatabaseEntityReturnList: MutableList<SecondaryTypes> = mutableListOf()

        type.map {
            pokemonSecondaryTypesDatabaseEntityReturnList.add(SecondaryTypes(it.name, it.bgColor))
        }
        return pokemonSecondaryTypesDatabaseEntityReturnList
    }

    fun transformToPokemonSupertypeDatabaseEntity(pokemonSupertype: SecondaryTypes): PokemonSupertypeDatabaseEntity =
        PokemonSupertypeDatabaseEntity(
            pokemonSupertype.name,
            pokemonSupertype.bgColor
        )
}
