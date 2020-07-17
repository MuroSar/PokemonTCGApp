package com.globant.data.mapper

import com.globant.data.database.entity.PokemonSupertypeDatabaseEntity
import com.globant.domain.entity.PokemonSupertype

class PokemonSupertypeDatabaseMapper :
    BaseMapper<List<PokemonSupertypeDatabaseEntity>, MutableList<PokemonSupertype>, MutableMap<String, Int>?> {
    override fun transform(type: List<PokemonSupertypeDatabaseEntity>, resources: MutableMap<String, Int>?): MutableList<PokemonSupertype> {
        val pokemonSupertypeDatabaseEntityReturnList: MutableList<PokemonSupertype> = mutableListOf()

        type.map {
            pokemonSupertypeDatabaseEntityReturnList.add(PokemonSupertype(it.name, it.bgColor))
        }
        return pokemonSupertypeDatabaseEntityReturnList
    }

    fun transformToPokemonSupertypeDatabaseEntity(pokemonSupertype: PokemonSupertype): PokemonSupertypeDatabaseEntity =
        PokemonSupertypeDatabaseEntity(
            pokemonSupertype.name,
            pokemonSupertype.bgColor
        )
}
