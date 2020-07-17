package com.globant.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globant.data.database.entity.PokemonSubtypeDatabaseEntity
import com.globant.data.database.entity.PokemonSupertypeDatabaseEntity
import com.globant.data.database.entity.PokemonTypeDatabaseEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_type_table")
    fun getPokemonTypes(): List<PokemonTypeDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonType(pokemonTypeDatabaseEntity: PokemonTypeDatabaseEntity)

    @Query("SELECT * FROM pokemon_supertype_table")
    fun getPokemonSupertypes(): List<PokemonSupertypeDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonSupertype(pokemonSupertypeDatabaseEntity: PokemonSupertypeDatabaseEntity)

    @Query("SELECT * FROM pokemon_subtype_table")
    fun getPokemonSubtypes(): List<PokemonSubtypeDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonSubtype(pokemonSubtypeDatabaseEntity: PokemonSubtypeDatabaseEntity)
}
