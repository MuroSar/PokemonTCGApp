package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.domain.entity.PokemonSupertype
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel.Data
import kotlinx.coroutines.Job

interface PokemonSupertypeContract {
    interface ViewModel {
        fun getPokemonSupertypesLiveData(): LiveData<Event<Data<List<PokemonSupertype>>>>
        fun getPokemonSupertypes(pokemonSupertypesResources: MutableMap<String, Int>): Job
    }
}
