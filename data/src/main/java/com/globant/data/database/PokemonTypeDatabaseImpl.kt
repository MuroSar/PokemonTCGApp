package com.globant.data.database

import com.globant.data.mapper.PokemonTypeRoomMapper
import com.globant.domain.database.PokemonTypeDatabase
import com.globant.domain.entity.PokemonType
import com.globant.domain.util.Constant.NOT_FOUND
import com.globant.domain.util.Result

class PokemonTypeDatabaseImpl(private val pokemonDao: PokemonDao) : PokemonTypeDatabase {

    private val pokemonTypeRoomMapper = PokemonTypeRoomMapper()

    override fun getLocalPokemonTypes(): Result<List<PokemonType>> {
        val pokemonTypes = pokemonDao.getPokemonTypes()
        pokemonTypes.let {
            if (it.isNotEmpty())
                return Result.Success(pokemonTypeRoomMapper.transform(pokemonDao.getPokemonTypes()))
        }
        return Result.Failure(Exception(NOT_FOUND))
    }

    override fun insertLocalPokemonTypes(pokemonTypes: List<PokemonType>) {
        pokemonTypes.forEach { pokemonDao.insertPokemonType(pokemonTypeRoomMapper.transformToPokemonTypeRoom(it)) }
    }
}
