package com.globant.data.database

import com.globant.data.mapper.PokemonSupertypeDatabaseMapper
import com.globant.domain.database.PokemonSupertypeDatabase
import com.globant.domain.entity.PokemonSupertype
import com.globant.domain.util.Constant
import com.globant.domain.util.Result

class PokemonSupertypeDatabaseImpl(private val pokemonDao: PokemonDao) : PokemonSupertypeDatabase {

    private val pokemonSupertypeDatabaseMapper = PokemonSupertypeDatabaseMapper()

    override fun getLocalPokemonSupertypes(): Result<List<PokemonSupertype>> {
        val pokemonSupertypes = pokemonDao.getPokemonSupertypes()
        pokemonSupertypes.let {
            if (it.isNotEmpty())
                return Result.Success(pokemonSupertypeDatabaseMapper.transform(pokemonDao.getPokemonSupertypes()))
        }
        return Result.Failure(Exception(Constant.SUPERTYPES_NOT_FOUND))
    }

    override fun insertLocalPokemonSupertypes(pokemonSupertypes: List<PokemonSupertype>) {
        pokemonSupertypes.forEach {
            pokemonDao.insertPokemonSupertype(
                pokemonSupertypeDatabaseMapper.transformToPokemonSupertypeDatabaseEntity(it)
            )
        }
    }
}
