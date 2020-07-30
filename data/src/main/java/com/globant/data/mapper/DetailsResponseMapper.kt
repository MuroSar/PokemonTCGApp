package com.globant.data.mapper

import com.globant.data.service.response.PokemonCardResponse
import com.globant.domain.entity.PokemonCardDetails
import com.globant.domain.util.EMPTY_STRING
import com.globant.domain.util.NONE
import com.globant.domain.util.ZERO_INT

class DetailsResponseMapper {
    fun transformToDetails(type: PokemonCardResponse): PokemonCardDetails = PokemonCardDetails(
        nationalPokedexNumber = type.nationalPokedexNumber ?: ZERO_INT,
        evolvesFrom = type.evolvesFrom ?: EMPTY_STRING,
        healthPoints = type.hp ?: NONE,
        number = type.number,
        artist = type.artist ?: EMPTY_STRING,
        rarity = type.rarity ?: EMPTY_STRING,
        series = type.series,
        set = type.set,
        setCode = type.setCode
    )
}
