package com.globant.domain.service

import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.Result

interface PokemonCardDetailService {
    fun getPokemonCardDetail(pokemonCardId: String): Result<PokemonCard>
}
