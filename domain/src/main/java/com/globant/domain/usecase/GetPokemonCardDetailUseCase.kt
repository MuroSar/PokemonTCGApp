package com.globant.domain.usecase

import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.Result

interface GetPokemonCardDetailUseCase {
    fun invoke(pokemonCardId: String): Result<PokemonCard>
}
