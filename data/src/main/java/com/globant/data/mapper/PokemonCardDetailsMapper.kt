package com.globant.data.mapper

import com.globant.data.service.response.PokemonCardResponse
import com.globant.domain.entity.PokemonCard
import com.globant.domain.entity.PokemonCardDetails

class PokemonCardDetailsMapper : BaseMapper<PokemonCardResponse, PokemonCard, MutableMap<String, Int>?> {

    override fun transform(type: PokemonCardResponse, resources: MutableMap<String, Int>?): PokemonCard =

        PokemonCard(
            type.id,
            type.name,
            type.imageUrl,
            type.types?.get(TYPE_VALUE),
            type.supertype,
            type.subtype,
            transformToDetails(type)
        )

    private fun transformToDetails(type: PokemonCardResponse): PokemonCardDetails = PokemonCardDetails(
        nationalPokedexNumber = type.nationalPokedexNumber.toString(),
        evolvesFrom = type.evolvesFrom,
        healthPoints = type.hp,
        number = type.number,
        artist = type.artist,
        rarity = type.rarity,
        series = type.series,
        set = type.set,
        setCode = type.setCode
    )

    companion object {
        private const val TYPE_VALUE = 0
    }
}
