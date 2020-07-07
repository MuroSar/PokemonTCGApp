package com.globant.data.mapper

import com.globant.domain.entity.PokemonType

class PokemonTypeMapper : BaseMapper<String, PokemonType> {
    override fun transform(type: String): PokemonType = PokemonType(type)

    fun transformPokemonTypesList(pokemonTypeResponse: List<String>): List<PokemonType> =
        pokemonTypeResponse.map { transform(it) }
}
