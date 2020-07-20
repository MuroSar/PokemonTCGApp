package com.globant.domain.service

import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.util.Result

interface PokemonSubtypesService {
    fun getPokemonSubtypes(pokemonSubtypesResources: MutableMap<String, Int>): Result<List<SecondaryTypes>>
}
