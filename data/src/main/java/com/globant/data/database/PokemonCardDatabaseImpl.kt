package com.globant.data.database

import com.globant.data.mapper.PokemonCardDatabaseMapper
import com.globant.domain.database.PokemonCardDatabase
import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.Result

class PokemonCardDatabaseImpl(private val pokemonDao: PokemonDao) : PokemonCardDatabase {
    private val pokemonCardDatabaseMapper = PokemonCardDatabaseMapper()

    override fun getLocalPokemonCardListType(pokemonCardGroupSelected: String): Result<List<PokemonCard>> {
        val pokemonSubtypes = pokemonDao.getPokemonSubtypes()
        pokemonSubtypes.let {
            if (it.isNotEmpty())
                return Result.Success(
                    pokemonCardDatabaseMapper.transform(
                        pokemonDao.getPokemonCardListType(
                            pokemonCardGroupSelected
                        )
                    )
                )
        }
        return Result.Failure(Exception(CARDS_NOT_FOUND))
    }

    override fun getLocalPokemonCardListSupertype(pokemonCardGroupSelected: String): Result<List<PokemonCard>> {
        val pokemonSubtypes = pokemonDao.getPokemonSubtypes()
        pokemonSubtypes.let {
            if (it.isNotEmpty())
                return Result.Success(
                    pokemonCardDatabaseMapper.transform(
                        pokemonDao.getPokemonCardListSupertype(
                            pokemonCardGroupSelected
                        )
                    )
                )
        }
        return Result.Failure(Exception(CARDS_NOT_FOUND))
    }

    override fun getLocalPokemonCardListSubtype(pokemonCardGroupSelected: String): Result<List<PokemonCard>> {
        val pokemonSubtypes = pokemonDao.getPokemonSubtypes()
        pokemonSubtypes.let {
            if (it.isNotEmpty())
                return Result.Success(
                    pokemonCardDatabaseMapper.transform(
                        pokemonDao.getPokemonCardListSubtype(
                            pokemonCardGroupSelected
                        )
                    )
                )
        }
        return Result.Failure(Exception(CARDS_NOT_FOUND))
    }

    override fun insertLocalPokemonCardList(pokemonCardList: List<PokemonCard>) {
        pokemonCardList.forEach {
            pokemonDao.insertPokemonCard(pokemonCardDatabaseMapper.transformToPokemonCardDatabaseEntity(it))
        }
    }

    companion object {
        private const val CARDS_NOT_FOUND = "Pokemon Cards Not Found"
    }
}
