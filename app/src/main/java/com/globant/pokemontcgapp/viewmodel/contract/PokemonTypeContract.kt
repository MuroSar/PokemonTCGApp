package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.domain.entity.PokemonType
import com.globant.pokemontcgapp.util.Data
import com.globant.pokemontcgapp.util.Event
import kotlinx.coroutines.Job

interface PokemonTypeContract {
    interface ViewModel {
        fun getPokemonTypesLiveData(): LiveData<Event<Data<List<PokemonType>>>>
        fun getPokemonTypes(): Job
    }
}
