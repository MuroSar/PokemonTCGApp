package com.globant.domain.service

import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.util.Result

interface PokemonSupertypesService {
    fun getPokemonSupertypes(pokemonSupertypesResources: MutableMap<String, Int>): Result<List<SecondaryTypes>>
}
