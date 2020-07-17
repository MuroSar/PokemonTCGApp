package com.globant.domain.usecase.implementation

import com.globant.domain.database.PokemonSubtypeDatabase
import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.service.PokemonSubtypesService
import com.globant.domain.usecase.GetPokemonSubtypesUseCase
import com.globant.domain.util.Result

class GetPokemonSubtypesUseCaseImpl(
    private val pokemonSubtypesService: PokemonSubtypesService,
    private val pokemonSubtypeDatabase: PokemonSubtypeDatabase
) : GetPokemonSubtypesUseCase {
    override fun invoke(pokemonSubtypesResources: MutableMap<String, Int>): Result<List<SecondaryTypes>> =
        when (val result = pokemonSubtypesService.getPokemonSubtypes(pokemonSubtypesResources)) {
            is Result.Success -> {
                pokemonSubtypeDatabase.insertLocalPokemonSubtypes(result.data)
                result
            }
            is Result.Failure -> {
                pokemonSubtypeDatabase.getLocalPokemonSubtypes()
            }
        }
}
