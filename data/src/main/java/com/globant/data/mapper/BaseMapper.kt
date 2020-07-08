package com.globant.data.mapper

interface BaseMapper<E, D> {
    fun transform(pokemonTypeName: E, pokemonTypeResources: Pair<Int, Int>): D
}
