package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel.Data
import kotlinx.coroutines.Job

interface PokemonCardDetailContract {
    interface ViewModel {
        fun getPokemonCardLiveData(): LiveData<Event<Data>>
        fun getPokemonCard(pokemonCardId: String): Job
    }
}
