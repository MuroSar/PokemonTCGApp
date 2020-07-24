package com.globant.domain.usecase.implementation

import com.globant.domain.database.PokemonCardDatabase
import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardListService
import com.globant.domain.usecase.GetPokemonCardListUseCase
import com.globant.domain.util.Result

class GetPokemonCardListUseCaseImpl(
    private val pokemonCardListService: PokemonCardListService,
    private val pokemonCardDatabase: PokemonCardDatabase
) : GetPokemonCardListUseCase {
    override fun invoke(pokemonCardGroup: String, pokemonCardGroupSelected: String): Result<List<PokemonCard>> =
        when (val result = pokemonCardListService.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)) {
            is Result.Success -> {
                pokemonCardDatabase.insertLocalPokemonCardList(result.data)
                result
            }
            is Result.Failure -> {
                when (pokemonCardGroup) {
                    TYPE -> pokemonCardDatabase.getLocalPokemonCardListType(pokemonCardGroupSelected)
                    SUPERTYPE -> pokemonCardDatabase.getLocalPokemonCardListSupertype(pokemonCardGroupSelected)
                    SUBTYPE -> pokemonCardDatabase.getLocalPokemonCardListSubtype(pokemonCardGroupSelected)
                    else -> Result.Failure(Exception())
                }
            }
        }

    companion object {
        private const val TYPE = "types"
        private const val SUPERTYPE = "supertype"
        private const val SUBTYPE = "subtype"
    }
}
