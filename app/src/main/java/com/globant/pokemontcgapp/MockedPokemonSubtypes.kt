package com.globant.pokemontcgapp

import com.globant.domain.entity.PokemonSubtype
import com.globant.pokemontcgapp.util.Color

class MockedPokemonSubtypes(
    val pokemonSubtypesList: List<PokemonSubtype> = listOf(
        PokemonSubtype("EX", Color.pokemon_type_colorless),
        PokemonSubtype("Special", Color.pokemon_type_grass),
        PokemonSubtype("Restored", Color.pokemon_type_fire),
        PokemonSubtype("Level Up", Color.pokemon_type_colorless),
        PokemonSubtype("MEGA", Color.pokemon_type_grass),
        PokemonSubtype("Technical Machine", Color.pokemon_type_fire),
        PokemonSubtype("Item", Color.pokemon_type_colorless),
        PokemonSubtype("Stadium", Color.pokemon_type_grass),
        PokemonSubtype("Supporter", Color.pokemon_type_fire),
        PokemonSubtype("Stage 1", Color.pokemon_type_colorless),
        PokemonSubtype("GX", Color.pokemon_type_grass),
        PokemonSubtype("Pokémon Tool", Color.pokemon_type_fire),
        PokemonSubtype("Basic", Color.pokemon_type_colorless),
        PokemonSubtype("LEGEND", Color.pokemon_type_grass),
        PokemonSubtype("Stage 2", Color.pokemon_type_fire),
        PokemonSubtype("BREAK", Color.pokemon_type_colorless),
        PokemonSubtype("Rocket's Secret Machine", Color.pokemon_type_grass)
    )
)
