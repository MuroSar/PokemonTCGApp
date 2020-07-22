package com.globant.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globant.data.database.entity.PokemonCardDatabaseEntity
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

    @Query("SELECT * FROM pokemon_card_table WHERE type = :pokemonCardGroupSelected")
    fun getPokemonCardListType(pokemonCardGroupSelected: String): List<PokemonCardDatabaseEntity>

    @Query("SELECT * FROM pokemon_card_table WHERE supertype = :pokemonCardGroupSelected")
    fun getPokemonCardListSupertype(pokemonCardGroupSelected: String): List<PokemonCardDatabaseEntity>

    @Query("SELECT * FROM pokemon_card_table WHERE subtype = :pokemonCardGroupSelected")
    fun getPokemonCardListSubtype(pokemonCardGroupSelected: String): List<PokemonCardDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonCard(pokemonCardDatabaseEntity: PokemonCardDatabaseEntity)
}
