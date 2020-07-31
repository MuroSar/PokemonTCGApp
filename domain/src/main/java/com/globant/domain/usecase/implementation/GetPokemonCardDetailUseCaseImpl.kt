package com.globant.domain.usecase.implementation

import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardDetailService
import com.globant.domain.usecase.GetPokemonCardDetailUseCase
import com.globant.domain.util.Result

class GetPokemonCardDetailUseCaseImpl(private val pokemonCardDetailService: PokemonCardDetailService) : GetPokemonCardDetailUseCase {
    override fun invoke(pokemonCardId: String): Result<PokemonCard> = pokemonCardDetailService.getPokemonCardDetail(pokemonCardId)
}
