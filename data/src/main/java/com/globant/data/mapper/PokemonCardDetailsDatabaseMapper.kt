package com.globant.data.mapper

import com.globant.data.database.entity.PokemonCardDatabaseEntity
import com.globant.domain.entity.PokemonCard

class PokemonCardDetailsDatabaseMapper : BaseMapper<PokemonCardDatabaseEntity, PokemonCard, MutableMap<String, Int>?> {

    override fun transform(type: PokemonCardDatabaseEntity, resources: MutableMap<String, Int>?): PokemonCard =

        PokemonCard(
            type.id,
            type.name,
            type.image,
            type.type,
            type.supertype,
            type.subtype,
            DetailsDatabaseEntityMapper().transformToDetails(type)
        )
}
