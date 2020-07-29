package com.globant.pokemontcgapp.viewmodel.contract

import android.view.View
import androidx.lifecycle.LiveData
import com.globant.domain.entity.SecondaryTypes
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel.Data
import kotlinx.coroutines.Job

interface PokemonSubtypeContract {
    interface ViewModel {
        fun getPokemonSubtypesLiveData(): LiveData<Event<Data>>
        fun getPokemonSubtypes(pokemonSubtypesResources: MutableMap<String, Int>): Job
        fun onPokemonSubtypeSelected(subtypeSelected: SecondaryTypes, sharedView: View)
    }
}
