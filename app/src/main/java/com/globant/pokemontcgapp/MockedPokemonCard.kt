package com.globant.pokemontcgapp

import com.globant.domain.entity.PokemonCard
import com.globant.domain.entity.PokemonCardDetails

class MockedPokemonCard(
    val pokemonCardSelected: PokemonCard = PokemonCard(
        "xy7-4",
        "Bellossom",
        "https://images.pokemontcg.io/xy7/4.png",
        "Grass",
        "Pokémon",
        "Stage 2",
        PokemonCardDetails(
            "182",
            "Gloom",
            "120",
            "4",
            "Mizue",
            "Uncommon",
            "XY",
            "Ancient Origins",
            "xy7"
        )
    )
)
