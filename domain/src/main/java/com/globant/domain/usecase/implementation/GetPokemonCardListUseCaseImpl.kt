package com.globant.domain.usecase.implementation

import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardListService
import com.globant.domain.usecase.GetPokemonCardListUseCase
import com.globant.domain.util.Result

class GetPokemonCardListUseCaseImpl(private val pokemonCardListService: PokemonCardListService) : GetPokemonCardListUseCase {
    override fun invoke(group: String, groupSelected: String): Result<List<PokemonCard>> =
        pokemonCardListService.getPokemonCardList(group, groupSelected)
}
