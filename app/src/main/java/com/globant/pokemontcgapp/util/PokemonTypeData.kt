package com.globant.pokemontcgapp.util

import com.globant.pokemontcgapp.MockedPokemonTypes

data class PokemonTypeData(val state: PokemonTypeState, val data: MockedPokemonTypes? = null)

enum class PokemonTypeState { LOADING, POKEMON_TYPE_DATA }
