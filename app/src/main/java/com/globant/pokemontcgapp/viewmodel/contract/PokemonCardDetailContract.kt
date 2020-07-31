package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel.Data
import kotlinx.coroutines.Job

interface PokemonCardDetailContract {
    interface ViewModel {
        fun getPokemonCardLiveData(): LiveData<Data>
        fun getPokemonCard(): Job
    }
}
