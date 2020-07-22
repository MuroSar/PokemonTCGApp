package com.globant.domain.database

import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.util.Result

interface PokemonSupertypeDatabase {
    fun getLocalPokemonSupertypes(): Result<List<SecondaryTypes>>
    fun insertLocalPokemonSupertypes(pokemonSupertypes: List<SecondaryTypes>)
}
