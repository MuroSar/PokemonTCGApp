package com.globant.domain.service

import com.globant.domain.entity.PokemonType
import com.globant.domain.util.Result

interface PokemonTypesService {
    fun getPokemonTypes(pokemonTypesResources: MutableMap<String, Pair<Int, Int>>): Result<List<PokemonType>>
}
