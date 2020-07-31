package com.globant.pokemontcgapp.util

import com.globant.domain.entity.PokemonCard
import com.globant.domain.entity.PokemonCardDetails

object Constant {
    const val COLORLESS = "Colorless"
    const val WATER = "Water"
    const val FIRE = "Fire"
    const val GRASS = "Grass"
    const val DARKNESS = "Darkness"
    const val METAL = "Metal"
    const val LIGHTNING = "Lightning"
    const val FIGHTING = "Fighting"
    const val PSYCHIC = "Psychic"
    const val FAIRY = "Fairy"
    const val DRAGON = "Dragon"
    const val ENERGY = "Energy"
    const val POKEMON = "Pokémon"
    const val TRAINER = "Trainer"
    const val EX = "EX"
    const val SPECIAL = "Special"
    const val RESTORED = "Restored"
    const val LEVEL_UP = "Level Up"
    const val MEGA = "MEGA"
    const val TECHNICAL_MACHINE = "Technical Machine"
    const val ITEM = "Item"
    const val STADIUM = "Stadium"
    const val SUPPORTER = "Supporter"
    const val STAGE_1 = "Stage 1"
    const val GX = "GX"
    const val POKEMON_TOOL = "Pokémon Tool"
    const val BASIC = "Basic"
    const val LEGEND = "LEGEND"
    const val STAGE_2 = "Stage 2"
    const val BREAK = "BREAK"
    const val ROCKETS_SECRET_MACHINE = "Rocket's Secret Machine"
    const val V = "V"
    const val V_MAX = "VMAX"
    const val TAG_TEAM = "TAG TEAM"
    const val RARE_HOLO = "Rare Holo"
    const val POKEMON_GROUP = "pokemonGroup"
    const val SELECTION = "selection"
    const val SELECTION_COLOR = "selectionColor"
    const val ID = "id"

    val pokemonCard: PokemonCard = PokemonCard(
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
}
