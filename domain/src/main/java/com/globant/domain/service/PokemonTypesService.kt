package com.globant.domain.service

import com.globant.domain.entity.PokemonType
import com.globant.domain.util.Result

interface PokemonTypesService {
    fun getPokemonTypesFromAPI(): Result<List<PokemonType>>
}
