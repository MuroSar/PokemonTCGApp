package com.globant.pokemontcgapp

import com.globant.domain.entity.PokemonSupertype
import com.globant.pokemontcgapp.util.Color

class MockedPokemonSupertypes(
    val pokemonSupertypesList: List<PokemonSupertype> = listOf(
        PokemonSupertype("Energy", Color.pokemon_type_colorless),
        PokemonSupertype("Pokémon", Color.pokemon_type_grass),
        PokemonSupertype("Trainer", Color.pokemon_type_fire)
    )
)
