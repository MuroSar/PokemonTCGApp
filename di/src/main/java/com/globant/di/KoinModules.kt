package com.globant.di

import com.globant.data.service.PokemonTypesServiceImpl
import com.globant.domain.service.PokemonTypesService
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.usecase.implementation.GetPokemonTypesUseCaseImpl
import org.koin.dsl.module

val serviceModule = module {
    single<PokemonTypesService> { PokemonTypesServiceImpl() }
}

val useCaseModule = module {
    single<GetPokemonTypesUseCase> { GetPokemonTypesUseCaseImpl(get()) }
}
