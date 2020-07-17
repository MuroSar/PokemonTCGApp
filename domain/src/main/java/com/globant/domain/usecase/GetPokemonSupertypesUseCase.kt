package com.globant.domain.usecase

import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.util.Result

interface GetPokemonSupertypesUseCase {
    fun invoke(pokemonSupertypesResources: MutableMap<String, Int>): Result<List<SecondaryTypes>>
}
