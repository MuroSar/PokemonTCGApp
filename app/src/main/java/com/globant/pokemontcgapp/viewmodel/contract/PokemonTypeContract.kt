package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.pokemontcgapp.util.PokemonTypeData
import kotlinx.coroutines.Job

interface PokemonTypeContract {
    interface ViewModel {
        fun getPokemonTypesLiveData(): LiveData<PokemonTypeData>
        fun getPokemonTypes(): Job
    }
}
