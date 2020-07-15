package com.globant.domain.database

import com.globant.domain.entity.PokemonType
import com.globant.domain.util.Result

interface PokemonTypeDatabase {
    fun getLocalPokemonTypes(): Result<List<PokemonType>>
    fun insertLocalPokemonTypes(pokemonTypes: List<PokemonType>)
}
