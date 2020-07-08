package com.globant.domain.service

import com.globant.domain.entity.PokemonType
import com.globant.domain.util.Result

interface PokemonTypesService {
    fun getPokemonTypesFromAPI(listOfPokemonTypesResources: List<Pair<Int, Int>>): Result<List<PokemonType>>
}
