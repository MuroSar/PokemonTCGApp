package com.globant.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.globant.data.database.entity.PokemonTypeRoom

@Database(entities = [PokemonTypeRoom::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}
