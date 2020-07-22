package com.globant.domain.service

import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.Result

interface PokemonCardListService {
    fun getPokemonCardList(pokemonCardGroup: String, pokemonCardGroupSelected: String): Result<List<PokemonCard>>
}
