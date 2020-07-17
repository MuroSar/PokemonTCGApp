package com.globant.domain.usecase.implementation

import com.globant.domain.database.PokemonSupertypeDatabase
import com.globant.domain.entity.PokemonSupertype
import com.globant.domain.service.PokemonSupertypesService
import com.globant.domain.usecase.GetPokemonSupertypesUseCase
import com.globant.domain.util.Result

class GetPokemonSupertypesUseCaseImpl(
    private val pokemonSupertypesService: PokemonSupertypesService,
    private val pokemonSupertypeDatabase: PokemonSupertypeDatabase
) : GetPokemonSupertypesUseCase {
    override fun invoke(pokemonSupertypesResources: MutableMap<String, Int>): Result<List<PokemonSupertype>> =
        when (val result = pokemonSupertypesService.getPokemonSupertypes(pokemonSupertypesResources)) {
            is Result.Success -> {
                pokemonSupertypeDatabase.insertLocalPokemonSupertypes(result.data)
                result
            }
            is Result.Failure -> {
                pokemonSupertypeDatabase.getLocalPokemonSupertypes()
            }
        }
}
