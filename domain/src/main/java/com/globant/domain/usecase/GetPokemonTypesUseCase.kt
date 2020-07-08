package com.globant.domain.usecase

import com.globant.domain.entity.PokemonType
import com.globant.domain.util.Result

interface GetPokemonTypesUseCase {
    fun invoke(listOfPokemonTypesResources: List<Pair<Int, Int>>): Result<List<PokemonType>>
}
