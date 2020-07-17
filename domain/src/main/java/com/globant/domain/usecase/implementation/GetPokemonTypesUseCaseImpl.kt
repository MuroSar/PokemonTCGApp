package com.globant.domain.usecase.implementation

import com.globant.domain.database.PokemonTypeDatabase
import com.globant.domain.entity.PokemonType
import com.globant.domain.service.PokemonTypesService
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.util.Result

class GetPokemonTypesUseCaseImpl(
    private val pokemonTypesService: PokemonTypesService,
    private val pokemonTypeDatabase: PokemonTypeDatabase
) : GetPokemonTypesUseCase {
    override fun invoke(pokemonTypesResources: MutableMap<String, Pair<Int, Int>>): Result<List<PokemonType>> =
        when (val result = pokemonTypesService.getPokemonTypes(pokemonTypesResources)) {
            is Result.Success -> {
                pokemonTypeDatabase.insertLocalPokemonTypes(result.data)
                result
            }
            is Result.Failure -> {
                pokemonTypeDatabase.getLocalPokemonTypes()
            }
        }
}
