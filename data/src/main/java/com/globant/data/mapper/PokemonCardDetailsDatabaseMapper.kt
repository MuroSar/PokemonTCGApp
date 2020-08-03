package com.globant.data.mapper

import com.globant.data.database.entity.PokemonCardDatabaseEntity
import com.globant.domain.entity.PokemonCard
import com.globant.domain.entity.PokemonCardDetails

class PokemonCardDetailsDatabaseMapper : BaseMapper<PokemonCardDatabaseEntity, PokemonCard, MutableMap<String, Int>?> {

    override fun transform(type: PokemonCardDatabaseEntity, resources: MutableMap<String, Int>?): PokemonCard =

        PokemonCard(
            type.id,
            type.name,
            type.image,
            type.type,
            type.supertype,
            type.subtype,
            transformToDetails(type)
        )

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
}
