package com.globant.data.database

import com.globant.data.mapper.PokemonTypeDatabaseMapper
import com.globant.domain.database.PokemonTypeDatabase
import com.globant.domain.entity.PokemonType
import com.globant.domain.util.Constant.TYPES_NOT_FOUND
import com.globant.domain.util.Result

class PokemonTypeDatabaseImpl(private val pokemonDao: PokemonDao) : PokemonTypeDatabase {

    private val pokemonTypeDatabaseMapper = PokemonTypeDatabaseMapper()

    override fun getLocalPokemonTypes(): Result<List<PokemonType>> {
        val pokemonTypes = pokemonDao.getPokemonTypes()
        pokemonTypes.let {
            if (it.isNotEmpty())
                return Result.Success(pokemonTypeDatabaseMapper.transform(pokemonDao.getPokemonTypes()))
        }
        return Result.Failure(Exception(TYPES_NOT_FOUND))
    }

    override fun insertLocalPokemonTypes(pokemonTypes: List<PokemonType>) {
        pokemonTypes.forEach { pokemonDao.insertPokemonType(pokemonTypeDatabaseMapper.transformToPokemonTypeDatabaseEntity(it)) }
    }
}
