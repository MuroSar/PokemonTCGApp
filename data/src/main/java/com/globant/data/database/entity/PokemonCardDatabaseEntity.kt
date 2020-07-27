package com.globant.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_card_table")
class PokemonCardDatabaseEntity(
    @PrimaryKey var id: String,
    var name: String,
    var image: String,
    var type: String?,
    var supertype: String?,
    var subtype: String?
)
