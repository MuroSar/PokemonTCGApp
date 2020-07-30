package com.globant.data.service.response

class PokemonCardResponse(
    var id: String,
    var name: String,
    var imageUrl: String,
    var types: List<String>?,
    var supertype: String?,
    var subtype: String?
)
