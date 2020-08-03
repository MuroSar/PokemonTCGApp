package com.globant.data.mapper

import com.globant.data.database.entity.PokemonCardDatabaseEntity
import com.globant.domain.entity.PokemonCard
import com.globant.domain.entity.PokemonCardDetails

class PokemonCardDatabaseMapper : BaseMapper<List<PokemonCardDatabaseEntity>, List<PokemonCard>, MutableMap<String, Int>?> {
    override fun transform(
        type: List<PokemonCardDatabaseEntity>,
        resources: MutableMap<String, Int>?
    ): MutableList<PokemonCard> {
        val pokemonCardDatabaseEntityReturnList: MutableList<PokemonCard> = mutableListOf()

        type.map {
            pokemonCardDatabaseEntityReturnList.add(
                PokemonCard(
                    it.id,
                    it.name,
                    it.image,
                    it.type,
                    it.supertype,
                    it.subtype,
                    transformToDetails(it)
                )
            )
        }
        return pokemonCardDatabaseEntityReturnList
    }

    private fun transformToDetails(type: PokemonCardDatabaseEntity): PokemonCardDetails = PokemonCardDetails(
        nationalPokedexNumber = type.nationalPokedexNumber,
        evolvesFrom = type.evolvesFrom,
        healthPoints = type.healthPoints,
        number = type.number,
        artist = type.artist,
        rarity = type.rarity,
        series = type.series,
        set = type.set,
        setCode = type.setCode
    )

    fun transformToPokemonCardDatabaseEntity(pokemonCard: PokemonCard): PokemonCardDatabaseEntity =
        PokemonCardDatabaseEntity(
            pokemonCard.id,
            pokemonCard.name,
            pokemonCard.image,
            pokemonCard.type,
            pokemonCard.supertype,
            pokemonCard.subtype,
            pokemonCard.details.nationalPokedexNumber,
            pokemonCard.details.evolvesFrom,
            pokemonCard.details.healthPoints,
            pokemonCard.details.number,
            pokemonCard.details.artist,
            pokemonCard.details.rarity,
            pokemonCard.details.series,
            pokemonCard.details.set,
            pokemonCard.details.setCode
        )
}
