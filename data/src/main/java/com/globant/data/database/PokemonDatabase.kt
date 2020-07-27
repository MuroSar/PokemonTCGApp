package com.globant.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.globant.data.database.entity.PokemonCardDatabaseEntity
import com.globant.data.database.entity.PokemonSubtypeDatabaseEntity
import com.globant.data.database.entity.PokemonSupertypeDatabaseEntity
import com.globant.data.database.entity.PokemonTypeDatabaseEntity

@Database(
    entities = [
        PokemonTypeDatabaseEntity::class,
        PokemonSupertypeDatabaseEntity::class,
        PokemonSubtypeDatabaseEntity::class,
        PokemonCardDatabaseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}
