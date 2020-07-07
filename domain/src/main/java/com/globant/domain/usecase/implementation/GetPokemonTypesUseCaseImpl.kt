package com.globant.domain.usecase.implementation

import com.globant.domain.entity.PokemonType
import com.globant.domain.service.PokemonTypesService
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.util.Result

class GetPokemonTypesUseCaseImpl(private val pokemonTypesService: PokemonTypesService) : GetPokemonTypesUseCase {
    override fun invoke(): Result<List<PokemonType>> = pokemonTypesService.getPokemonTypesFromAPI()
}
