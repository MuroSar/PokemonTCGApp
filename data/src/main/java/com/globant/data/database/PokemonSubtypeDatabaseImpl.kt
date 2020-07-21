package com.globant.data.database

import com.globant.data.mapper.PokemonSubtypeDatabaseMapper
import com.globant.domain.database.PokemonSubtypeDatabase
import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.util.Constant.SUBTYPES_NOT_FOUND
import com.globant.domain.util.Result

class PokemonSubtypeDatabaseImpl(private val pokemonDao: PokemonDao) : PokemonSubtypeDatabase {

    private val pokemonSubtypeDatabaseMapper = PokemonSubtypeDatabaseMapper()

    override fun getLocalPokemonSubtypes(): Result<List<SecondaryTypes>> {
        val pokemonSubtypes = pokemonDao.getPokemonSubtypes()
        pokemonSubtypes.let {
            if (it.isNotEmpty())
                return Result.Success(pokemonSubtypeDatabaseMapper.transform(pokemonDao.getPokemonSubtypes()))
        }
        return Result.Failure(Exception(SUBTYPES_NOT_FOUND))
    }

    override fun insertLocalPokemonSubtypes(pokemonSubtypes: List<SecondaryTypes>) {
        pokemonSubtypes.forEach {
            pokemonDao.insertPokemonSubtype(pokemonSubtypeDatabaseMapper.transformToPokemonSubtypeDatabaseEntity(it))
        }
    }
}
