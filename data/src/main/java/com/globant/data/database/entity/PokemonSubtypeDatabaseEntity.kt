package com.globant.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_subtype_table")
data class PokemonSubtypeDatabaseEntity(
    @PrimaryKey var name: String,
    var bgColor: Int
)
