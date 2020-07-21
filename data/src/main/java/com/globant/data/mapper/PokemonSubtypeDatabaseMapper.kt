package com.globant.data.mapper

import com.globant.data.database.entity.PokemonSubtypeDatabaseEntity
import com.globant.domain.entity.SecondaryTypes

class PokemonSubtypeDatabaseMapper : BaseMapper<List<PokemonSubtypeDatabaseEntity>, MutableList<SecondaryTypes>, MutableMap<String, Int>?> {
    override fun transform(
        type: List<PokemonSubtypeDatabaseEntity>,
        resources: MutableMap<String, Int>?
    ): MutableList<SecondaryTypes> {
        val pokemonSecondaryTypesDatabaseEntityReturnList: MutableList<SecondaryTypes> = mutableListOf()

        type.map {
            pokemonSecondaryTypesDatabaseEntityReturnList.add(SecondaryTypes(it.name, it.bgColor))
        }
        return pokemonSecondaryTypesDatabaseEntityReturnList
    }

    fun transformToPokemonSubtypeDatabaseEntity(pokemonSubtype: SecondaryTypes): PokemonSubtypeDatabaseEntity =
        PokemonSubtypeDatabaseEntity(
            pokemonSubtype.name,
            pokemonSubtype.bgColor
        )
}
