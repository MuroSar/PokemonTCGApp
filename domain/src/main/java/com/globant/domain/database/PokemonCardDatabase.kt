package com.globant.domain.database

import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.Result

interface PokemonCardDatabase {
    fun getLocalPokemonCardListType(pokemonCardGroupSelected: String): Result<List<PokemonCard>>
    fun getLocalPokemonCardListSupertype(pokemonCardGroupSelected: String): Result<List<PokemonCard>>
    fun getLocalPokemonCardListSubtype(pokemonCardGroupSelected: String): Result<List<PokemonCard>>
    fun insertLocalPokemonCardList(pokemonCardList: List<PokemonCard>)
}
