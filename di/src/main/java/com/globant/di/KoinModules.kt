package com.globant.di

import androidx.room.Room
import com.globant.data.database.PokemonCardDatabaseImpl
import com.globant.data.database.PokemonDatabase
import com.globant.data.database.PokemonSubtypeDatabaseImpl
import com.globant.data.database.PokemonSupertypeDatabaseImpl
import com.globant.data.database.PokemonTypeDatabaseImpl
import com.globant.data.service.PokemonCardDetailServiceImpl
import com.globant.data.service.PokemonCardListServiceImpl
import com.globant.data.service.PokemonSubtypesServiceImpl
import com.globant.data.service.PokemonSupertypesServiceImpl
import com.globant.data.service.PokemonTypesServiceImpl
import com.globant.domain.database.PokemonCardDatabase
import com.globant.domain.database.PokemonSubtypeDatabase
import com.globant.domain.database.PokemonSupertypeDatabase
import com.globant.domain.database.PokemonTypeDatabase
import com.globant.domain.service.PokemonCardDetailService
import com.globant.domain.service.PokemonCardListService
import com.globant.domain.service.PokemonSubtypesService
import com.globant.domain.service.PokemonSupertypesService
import com.globant.domain.service.PokemonTypesService
import com.globant.domain.usecase.GetPokemonCardDetailUseCase
import com.globant.domain.usecase.GetPokemonCardListUseCase
import com.globant.domain.usecase.GetPokemonSubtypesUseCase
import com.globant.domain.usecase.GetPokemonSupertypesUseCase
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.usecase.implementation.GetPokemonCardDetailUseCaseImpl
import com.globant.domain.usecase.implementation.GetPokemonCardListUseCaseImpl
import com.globant.domain.usecase.implementation.GetPokemonSubtypesUseCaseImpl
import com.globant.domain.usecase.implementation.GetPokemonSupertypesUseCaseImpl
import com.globant.domain.usecase.implementation.GetPokemonTypesUseCaseImpl
import org.koin.dsl.module

val serviceModule = module {
    single<PokemonTypesService> { PokemonTypesServiceImpl() }
    single<PokemonSupertypesService> { PokemonSupertypesServiceImpl() }
    single<PokemonSubtypesService> { PokemonSubtypesServiceImpl() }
    single<PokemonCardListService> { PokemonCardListServiceImpl() }
    single<PokemonCardDetailService> { PokemonCardDetailServiceImpl() }
}

val useCaseModule = module {
    single<GetPokemonTypesUseCase> { GetPokemonTypesUseCaseImpl(get(), get()) }
    single<GetPokemonSupertypesUseCase> { GetPokemonSupertypesUseCaseImpl(get(), get()) }
    single<GetPokemonSubtypesUseCase> { GetPokemonSubtypesUseCaseImpl(get(), get()) }
    single<GetPokemonCardListUseCase> { GetPokemonCardListUseCaseImpl(get(), get()) }
    single<GetPokemonCardDetailUseCase> { GetPokemonCardDetailUseCaseImpl(get()) }
}

val databaseModule = module {
    single { Room.databaseBuilder(get(), PokemonDatabase::class.java, DATA_BASE_NAME).build() }
    single { get<PokemonDatabase>().pokemonDao() }
    single<PokemonTypeDatabase> { PokemonTypeDatabaseImpl(get()) }
    single<PokemonSupertypeDatabase> { PokemonSupertypeDatabaseImpl(get()) }
    single<PokemonSubtypeDatabase> { PokemonSubtypeDatabaseImpl(get()) }
    single<PokemonCardDatabase> { PokemonCardDatabaseImpl(get()) }
}

private const val DATA_BASE_NAME = "pokemon_database"
