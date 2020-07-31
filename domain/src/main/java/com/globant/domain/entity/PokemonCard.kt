package com.globant.domain.entity

class PokemonCard(
    var id: String,
    var name: String,
    var image: String,
    var type: String?,
    var supertype: String?,
    var subtype: String?,
    var details: PokemonCardDetails? = null
)
