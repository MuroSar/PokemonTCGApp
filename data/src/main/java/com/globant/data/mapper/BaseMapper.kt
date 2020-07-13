package com.globant.data.mapper

import com.globant.domain.entity.PokemonType

interface BaseMapper<E, D> {
    fun transformPokemonTypesList(pokemonTypeResponse: E, pokemonTypesResources: MutableMap<String, Pair<Int, Int>>): List<PokemonType>
}
