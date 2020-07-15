package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel
import kotlinx.coroutines.Job

interface PokemonSupertypeContract {
    interface ViewModel {
        fun getPokemonSupertypesLiveData(): LiveData<PokemonSupertypeViewModel.Data>
        fun getPokemonSupertypes(): Job
    }
}
