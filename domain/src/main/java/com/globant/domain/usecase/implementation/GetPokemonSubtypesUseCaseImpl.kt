package com.globant.domain.usecase.implementation

import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.service.PokemonSubtypesService
import com.globant.domain.usecase.GetPokemonSubtypesUseCase
import com.globant.domain.util.Result

class GetPokemonSubtypesUseCaseImpl(private val pokemonSubtypesService: PokemonSubtypesService) : GetPokemonSubtypesUseCase {
    override fun invoke(pokemonSubtypesResources: MutableMap<String, Int>): Result<List<SecondaryTypes>> =
        pokemonSubtypesService.getPokemonSubtypes(pokemonSubtypesResources)
}
