package com.globant.domain.database

import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.util.Result

interface PokemonSubtypeDatabase {
    fun getLocalPokemonSubtypes(): Result<List<SecondaryTypes>>
    fun insertLocalPokemonSubtypes(pokemonSubtypes: List<SecondaryTypes>)
}
