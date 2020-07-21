package com.globant.di

import androidx.room.Room
import com.globant.data.database.PokemonDatabase
import com.globant.data.database.PokemonSupertypeDatabaseImpl
import com.globant.data.database.PokemonTypeDatabaseImpl
import com.globant.data.service.PokemonSubtypesServiceImpl
import com.globant.data.service.PokemonSupertypesServiceImpl
import com.globant.data.service.PokemonTypesServiceImpl
import com.globant.domain.database.PokemonSupertypeDatabase
import com.globant.domain.database.PokemonTypeDatabase
import com.globant.domain.service.PokemonSubtypesService
import com.globant.domain.service.PokemonSupertypesService
import com.globant.domain.service.PokemonTypesService
import com.globant.domain.usecase.GetPokemonSubtypesUseCase
import com.globant.domain.usecase.GetPokemonSupertypesUseCase
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.usecase.implementation.GetPokemonSubtypesUseCaseImpl
import com.globant.domain.usecase.implementation.GetPokemonSupertypesUseCaseImpl
import com.globant.domain.usecase.implementation.GetPokemonTypesUseCaseImpl
import org.koin.dsl.module

val serviceModule = module {
    single<PokemonTypesService> { PokemonTypesServiceImpl() }
    single<PokemonSupertypesService> { PokemonSupertypesServiceImpl() }
    single<PokemonSubtypesService> { PokemonSubtypesServiceImpl() }
}

val useCaseModule = module {
    single<GetPokemonTypesUseCase> { GetPokemonTypesUseCaseImpl(get(), get()) }
    single<GetPokemonSupertypesUseCase> { GetPokemonSupertypesUseCaseImpl(get(), get()) }
    single<GetPokemonSubtypesUseCase> { GetPokemonSubtypesUseCaseImpl(get()) }
}

val databaseModule = module {
    single { Room.databaseBuilder(get(), PokemonDatabase::class.java, DATA_BASE_NAME).build() }
    single { get<PokemonDatabase>().pokemonDao() }
    single<PokemonTypeDatabase> { PokemonTypeDatabaseImpl(get()) }
    single<PokemonSupertypeDatabase> { PokemonSupertypeDatabaseImpl(get()) }
}

private const val DATA_BASE_NAME = "pokemon_database"
