package com.globant.domain.database

import com.globant.domain.entity.PokemonSupertype
import com.globant.domain.util.Result

interface PokemonSupertypeDatabase {
    fun getLocalPokemonSupertypes(): Result<List<PokemonSupertype>>
    fun insertLocalPokemonSupertypes(pokemonSupertypes: List<PokemonSupertype>)
}
