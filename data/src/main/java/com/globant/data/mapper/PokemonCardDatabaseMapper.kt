package com.globant.data.mapper

import com.globant.data.database.entity.PokemonCardDatabaseEntity
import com.globant.domain.entity.PokemonCard

class PokemonCardDatabaseMapper : BaseMapper<List<PokemonCardDatabaseEntity>, List<PokemonCard>, MutableMap<String, Int>?> {
    override fun transform(
        type: List<PokemonCardDatabaseEntity>,
        resources: MutableMap<String, Int>?
    ): MutableList<PokemonCard> {
        val pokemonCardDatabaseEntityReturnList: MutableList<PokemonCard> = mutableListOf()

        type.map {
            pokemonCardDatabaseEntityReturnList.add(PokemonCard(it.id, it.name, it.image, it.type, it.supertype, it.subtype))
        }
        return pokemonCardDatabaseEntityReturnList
    }

    fun transformToPokemonCardDatabaseEntity(pokemonCard: PokemonCard): PokemonCardDatabaseEntity =
        PokemonCardDatabaseEntity(
            pokemonCard.id,
            pokemonCard.name,
            pokemonCard.image,
            pokemonCard.type,
            pokemonCard.supertype,
            pokemonCard.subtype
        )
}
