package com.globant.pokemontcgapp.di

import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel
import com.globant.pokemontcgapp.viewmodel.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashScreenViewModel() }
    viewModel { PokemonTypeViewModel(get()) }
    viewModel { PokemonSupertypeViewModel(get()) }
    viewModel { PokemonSubtypeViewModel(get()) }
    viewModel { PokemonCardListViewModel(get()) }
}
