package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel.Data
import kotlinx.coroutines.Job

interface PokemonCardListContract {
    interface ViewModel {
        fun getPokemonCardListLiveData(): LiveData<Data>
        fun getPokemonCardList(): Job
    }
}
