package com.globant.domain.usecase.implementation

import com.globant.domain.entity.PokemonSupertype
import com.globant.domain.service.PokemonSupertypesService
import com.globant.domain.usecase.GetPokemonSupertypesUseCase
import com.globant.domain.util.Result

class GetPokemonSupertypesUseCaseImpl(private val pokemonSupertypesService: PokemonSupertypesService) : GetPokemonSupertypesUseCase {
    override fun invoke(pokemonSupertypesResources: MutableMap<String, Int>): Result<List<PokemonSupertype>> =
        pokemonSupertypesService.getPokemonSupertypesFromAPI(pokemonSupertypesResources)
}
