package com.globant.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_type_table")
data class PokemonTypeDatabaseEntity(
    @PrimaryKey var name: String,
    var image: Int,
    var bgColor: Int
)
