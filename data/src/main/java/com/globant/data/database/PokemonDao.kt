package com.globant.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globant.data.database.entity.PokemonTypeRoom

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_type_table")
    fun getPokemonTypes(): List<PokemonTypeRoom>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPokemonType(pokemonTypeRoom: PokemonTypeRoom)
}
