package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel
import kotlinx.coroutines.Job

interface PokemonSubtypeContract {
    interface ViewModel {
        fun getPokemonSubtypesLiveData(): LiveData<PokemonSubtypeViewModel.Data>
        fun getPokemonSubtypes(): Job
    }
}
