package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.pokemontcgapp.MockedPokemonCardList
import com.globant.pokemontcgapp.viewmodel.contract.PokemonCardListContract
import kotlinx.coroutines.launch

class PokemonCardListViewModel :
    ViewModel(), PokemonCardListContract.ViewModel {

    private val pokemonCardListMutableLiveData = MutableLiveData<Data>()
    override fun getPokemonCardListLiveData(): LiveData<Data> = pokemonCardListMutableLiveData

    override fun getPokemonCardList() = viewModelScope.launch {
        // TODO: Recover the list of cards from the API using a use case
        pokemonCardListMutableLiveData.value = Data(Status.SUCCESS, MockedPokemonCardList())
    }

    data class Data(var status: Status, val data: MockedPokemonCardList? = null)

    enum class Status {
        SUCCESS
    }
}
