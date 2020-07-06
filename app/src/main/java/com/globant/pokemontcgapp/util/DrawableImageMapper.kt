package com.globant.pokemontcgapp.util

import com.globant.pokemontcgapp.R

fun pokemonTypeImageMapper(type: String): Int =
    when (type) {
        "Colorless" -> R.drawable.pokemon_colorless_type
        "Water" -> R.drawable.pokemon_water_type
        "Fire" -> R.drawable.pokemon_fire_type
        "Grass" -> R.drawable.pokemon_grass_type
        "Darkness" -> R.drawable.pokemon_darkness_type
        "Metal" -> R.drawable.pokemon_metal_type
        "Lightning" -> R.drawable.pokemon_lightning_type
        "Fighting" -> R.drawable.pokemon_fighting_type
        "Psychic" -> R.drawable.pokemon_psychic_type
        "Fairy" -> R.drawable.pokemon_fairy_type
        "Dragon" -> R.drawable.pokemon_dragon_type
        else -> R.drawable.pokemon_colorless_type
    }
