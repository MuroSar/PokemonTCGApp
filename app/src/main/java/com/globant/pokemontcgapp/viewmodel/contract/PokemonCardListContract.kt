package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.domain.entity.PokemonCard
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel.Data
import kotlinx.coroutines.Job

interface PokemonCardListContract {
    interface ViewModel {
        fun getPokemonCardListLiveData(): LiveData<Event<Data<List<PokemonCard>>>>
        fun getPokemonCardList(group: String, groupSelected: String): Job
    }
}
