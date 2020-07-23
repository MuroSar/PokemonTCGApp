package com.globant.domain.usecase

import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.Result

interface GetPokemonCardListUseCase {
    fun invoke(group: String, groupSelected: String): Result<List<PokemonCard>>
}
