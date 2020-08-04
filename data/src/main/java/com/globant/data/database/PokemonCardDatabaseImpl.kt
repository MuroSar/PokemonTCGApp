package com.globant.data.database

import com.globant.data.database.entity.PokemonCardDatabaseEntity
import com.globant.data.mapper.PokemonCardDatabaseMapper
import com.globant.data.mapper.PokemonCardDetailsDatabaseMapper
import com.globant.domain.database.PokemonCardDatabase
import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.Result

class PokemonCardDatabaseImpl(private val pokemonDao: PokemonDao) : PokemonCardDatabase {
    private val pokemonCardDatabaseMapper = PokemonCardDatabaseMapper()
    private val pokemonCardDetailsDatabaseMapper = PokemonCardDetailsDatabaseMapper()

    override fun getLocalPokemonCardList(pokemonCardGroup: String, pokemonCardGroupSelected: String): Result<List<PokemonCard>> {
        val pokemonCardList: List<PokemonCardDatabaseEntity> =
            when (pokemonCardGroup) {
                TYPE -> pokemonDao.getPokemonCardListType(pokemonCardGroupSelected)
                SUPERTYPE -> pokemonDao.getPokemonCardListSupertype(pokemonCardGroupSelected)
                SUBTYPE -> pokemonDao.getPokemonCardListSubtype(pokemonCardGroupSelected)
                else -> emptyList()
            }

        pokemonCardList.let {
            if (it.isNotEmpty())
                return Result.Success(pokemonCardDatabaseMapper.transform(it))
        }
        return Result.Failure(Exception(CARDS_NOT_FOUND))
    }

    override fun insertLocalPokemonCardList(pokemonCardList: List<PokemonCard>) {
        pokemonCardList.forEach {
            pokemonDao.insertPokemonCard(pokemonCardDatabaseMapper.transformToPokemonCardDatabaseEntity(it))
        }
    }

    override fun getLocalPokemonCard(pokemonCardId: String): Result<PokemonCard> {

        try {
            val pokemonCardDatabaseEntity: PokemonCardDatabaseEntity = pokemonDao.getPokemonCard(pokemonCardId)

            pokemonCardDatabaseEntity.let {
                return Result.Success(pokemonCardDetailsDatabaseMapper.transform(it))
            }
        } catch (e: Exception) {
            return Result.Failure(Exception(CARD_NOT_FOUND))
        }
    }

    override fun insertLocalPokemonCard(pokemonCard: PokemonCard) {
        pokemonDao.insertPokemonCard(pokemonCardDatabaseMapper.transformToPokemonCardDatabaseEntity(pokemonCard))
    }

    companion object {
        private const val CARDS_NOT_FOUND = "Pokemon Cards Not Found"
        private const val CARD_NOT_FOUND = "Pokemon Card Not Found"
        private const val TYPE = "types"
        private const val SUPERTYPE = "supertype"
        private const val SUBTYPE = "subtype"
    }
}
