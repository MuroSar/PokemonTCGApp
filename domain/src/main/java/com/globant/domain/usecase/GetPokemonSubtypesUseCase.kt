package com.globant.domain.usecase

import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.util.Result

interface GetPokemonSubtypesUseCase {
    fun invoke(pokemonSubtypesResources: MutableMap<String, Int>): Result<List<SecondaryTypes>>
}
