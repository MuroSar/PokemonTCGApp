package com.globant.domain.database

import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.Result

interface PokemonCardDatabase {
    fun getLocalPokemonCardList(pokemonCardGroup: String, pokemonCardGroupSelected: String): Result<List<PokemonCard>>
    fun insertLocalPokemonCardList(pokemonCardList: List<PokemonCard>)
    fun getLocalPokemonCard(pokemonCardId: String): Result<PokemonCard>
    fun insertLocalPokemonCard(pokemonCard: PokemonCard)
}
