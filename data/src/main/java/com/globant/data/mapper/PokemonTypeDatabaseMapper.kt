package com.globant.data.mapper

import com.globant.data.database.entity.PokemonTypeDatabaseEntity
import com.globant.domain.entity.PokemonType

class PokemonTypeDatabaseMapper :
    BaseMapper<List<PokemonTypeDatabaseEntity>, MutableList<PokemonType>, MutableMap<String, Pair<Int, Int>>?> {
    override fun transform(
        type: List<PokemonTypeDatabaseEntity>,
        resources: MutableMap<String, Pair<Int, Int>>?
    ): MutableList<PokemonType> {
        val pokemonTypeDatabaseEntityReturnList: MutableList<PokemonType> = mutableListOf()

        type.map {
            pokemonTypeDatabaseEntityReturnList.add(PokemonType(it.name, it.image, it.bgColor))
        }
        return pokemonTypeDatabaseEntityReturnList
    }

    fun transformToPokemonTypeDatabaseEntity(pokemonType: PokemonType): PokemonTypeDatabaseEntity =
        PokemonTypeDatabaseEntity(
            pokemonType.name,
            pokemonType.image,
            pokemonType.bgColor
        )
}
