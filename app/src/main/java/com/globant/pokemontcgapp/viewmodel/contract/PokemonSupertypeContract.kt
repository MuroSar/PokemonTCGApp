package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.domain.entity.SecondaryTypes
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel.Data
import kotlinx.coroutines.Job

interface PokemonSupertypeContract {
    interface ViewModel {
        fun getPokemonSupertypesLiveData(): LiveData<Event<Data>>
        fun getPokemonSupertypes(pokemonSupertypesResources: MutableMap<String, Int>): Job
        fun onPokemonSupertypeSelected(supertypeSelected: SecondaryTypes)
    }
}
