package com.globant.domain.usecase.implementation

import com.globant.domain.database.PokemonCardDatabase
import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardDetailService
import com.globant.domain.usecase.GetPokemonCardDetailUseCase
import com.globant.domain.util.Result

class GetPokemonCardDetailUseCaseImpl(
    private val pokemonCardDetailService: PokemonCardDetailService,
    private val pokemonCardDatabase: PokemonCardDatabase
) : GetPokemonCardDetailUseCase {
    override fun invoke(pokemonCardId: String): Result<PokemonCard> =
        when (val result = pokemonCardDetailService.getPokemonCardDetail(pokemonCardId)) {
            is Result.Success -> {
                pokemonCardDatabase.insertLocalPokemonCard(result.data)
                result
            }
            is Result.Failure -> {
                pokemonCardDatabase.getLocalPokemonCard(pokemonCardId)
            }
        }
}
