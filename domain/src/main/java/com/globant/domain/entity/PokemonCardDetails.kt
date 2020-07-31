package com.globant.domain.entity

import com.globant.domain.util.NONE

class PokemonCardDetails(
    var nationalPokedexNumber: String?,
    var evolvesFrom: String?,
    var healthPoints: String = NONE,
    var number: String,
    var artist: String,
    var rarity: String,
    var series: String,
    var set: String,
    var setCode: String
)
