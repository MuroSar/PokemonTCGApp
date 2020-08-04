package com.globant.data.mapper

import com.globant.data.database.entity.PokemonCardDatabaseEntity
import com.globant.domain.entity.PokemonCardDetails

class DetailsDatabaseEntityMapper {

    fun transformToDetails(type: PokemonCardDatabaseEntity): PokemonCardDetails = PokemonCardDetails(
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
