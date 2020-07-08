package com.globant.data.mapper

import com.globant.domain.entity.PokemonType

class PokemonTypeMapper : BaseMapper<String, PokemonType> {

    fun transformPokemonTypesList(
        pokemonTypeResponse: List<String>,
        listOfPokemonTypesResources: List<Pair<Int, Int>>
    ): List<PokemonType> =
        pokemonTypeResponse.map { transform(it, getTypeResources(it, listOfPokemonTypesResources)) }

    override fun transform(pokemonTypeName: String, pokemonTypeResources: Pair<Int, Int>): PokemonType {
        return PokemonType(pokemonTypeName, pokemonTypeResources.first, pokemonTypeResources.second)
    }

    private fun getTypeResources(type: String, listOfPokemonTypesResources: List<Pair<Int, Int>>): Pair<Int, Int> {
        return when (type) {
            COLORLESS -> listOfPokemonTypesResources[ZERO]
            WATER -> listOfPokemonTypesResources[ONE]
            FIRE -> listOfPokemonTypesResources[TWO]
            GRASS -> listOfPokemonTypesResources[THREE]
            DARKNESS -> listOfPokemonTypesResources[FOUR]
            METAL -> listOfPokemonTypesResources[FIVE]
            LIGHTNING -> listOfPokemonTypesResources[SIX]
            FIGHTING -> listOfPokemonTypesResources[SEVEN]
            PSYCHIC -> listOfPokemonTypesResources[EIGHT]
            FAIRY -> listOfPokemonTypesResources[NINE]
            DRAGON -> listOfPokemonTypesResources[TEN]
            else -> listOfPokemonTypesResources[ZERO]
        }
    }

    companion object {
        private const val COLORLESS = "Colorless"
        private const val WATER = "Water"
        private const val FIRE = "Fire"
        private const val GRASS = "Grass"
        private const val DARKNESS = "Darkness"
        private const val METAL = "Metal"
        private const val LIGHTNING = "Lightning"
        private const val FIGHTING = "Fighting"
        private const val PSYCHIC = "Psychic"
        private const val FAIRY = "Fairy"
        private const val DRAGON = "Dragon"
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
        private const val THREE = 3
        private const val FOUR = 4
        private const val FIVE = 5
        private const val SIX = 6
        private const val SEVEN = 7
        private const val EIGHT = 8
        private const val NINE = 9
        private const val TEN = 10
    }
}
