package com.globant.data.service.response

class PokemonCardResponse(
    var id: String,
    var name: String,
    var nationalPokedexNumber: Int?,
    var imageUrl: String,
    var types: List<String>?,
    var supertype: String?,
    var subtype: String?,
    var evolvesFrom: String?,
    var hp: String?,
    var number: String,
    var artist: String,
    var rarity: String,
    var series: String,
    var set: String,
    var setCode: String
)
