package com.globant.data.mapper

import com.globant.data.service.response.PokemonCardResponse
import com.globant.domain.entity.PokemonCard

class PokemonCardDetailsMapper : BaseMapper<PokemonCardResponse, PokemonCard, MutableMap<String, Int>?> {

    override fun transform(type: PokemonCardResponse, resources: MutableMap<String, Int>?): PokemonCard =

        PokemonCard(
            type.id,
            type.name,
            type.imageUrl,
            type.types?.get(TYPE_VALUE),
            type.supertype,
            type.subtype,
            DetailsResponseMapper().transformToDetails(type)
        )

    companion object {
        private const val TYPE_VALUE = 0
    }
}
