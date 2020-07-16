package com.globant.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_supertype_table")
data class PokemonSupertypeRoom(
    @PrimaryKey var name: String,
    var bgColor: Int
)
