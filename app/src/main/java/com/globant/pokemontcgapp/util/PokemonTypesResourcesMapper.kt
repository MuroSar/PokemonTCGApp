package com.globant.pokemontcgapp.util

import com.globant.pokemontcgapp.R
import com.globant.pokemontcgapp.util.Constant.COLORLESS
import com.globant.pokemontcgapp.util.Constant.DARKNESS
import com.globant.pokemontcgapp.util.Constant.DRAGON
import com.globant.pokemontcgapp.util.Constant.FAIRY
import com.globant.pokemontcgapp.util.Constant.FIGHTING
import com.globant.pokemontcgapp.util.Constant.FIRE
import com.globant.pokemontcgapp.util.Constant.GRASS
import com.globant.pokemontcgapp.util.Constant.LIGHTNING
import com.globant.pokemontcgapp.util.Constant.METAL
import com.globant.pokemontcgapp.util.Constant.PSYCHIC
import com.globant.pokemontcgapp.util.Constant.WATER

typealias Drawable = R.drawable
typealias Color = R.color
typealias StringResource = R.string

val pokemonTypesResources = mutableMapOf(
    Pair(COLORLESS, Pair(Drawable.pokemon_colorless_type, Color.pokemon_type_colorless)),
    Pair(WATER, Pair(Drawable.pokemon_water_type, Color.pokemon_type_water)),
    Pair(FIRE, Pair(Drawable.pokemon_fire_type, Color.pokemon_type_fire)),
    Pair(GRASS, Pair(Drawable.pokemon_grass_type, Color.pokemon_type_grass)),
    Pair(DARKNESS, Pair(Drawable.pokemon_darkness_type, Color.pokemon_type_darkness)),
    Pair(METAL, Pair(Drawable.pokemon_metal_type, Color.pokemon_type_metal)),
    Pair(LIGHTNING, Pair(Drawable.pokemon_lightning_type, Color.pokemon_type_lightning)),
    Pair(FIGHTING, Pair(Drawable.pokemon_fighting_type, Color.pokemon_type_fighting)),
    Pair(PSYCHIC, Pair(Drawable.pokemon_psychic_type, Color.pokemon_type_psychic)),
    Pair(FAIRY, Pair(Drawable.pokemon_fairy_type, Color.pokemon_type_fairy)),
    Pair(DRAGON, Pair(Drawable.pokemon_dragon_type, Color.pokemon_type_dragon))
)
