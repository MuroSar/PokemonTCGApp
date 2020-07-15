package com.globant.domain.usecase

import com.globant.domain.entity.PokemonSupertype
import com.globant.domain.util.Result

interface GetPokemonSupertypesUseCase {
    fun invoke(pokemonSupertypesResources: MutableMap<String, Int>): Result<List<PokemonSupertype>>
}
