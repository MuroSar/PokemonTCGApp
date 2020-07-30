package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entity.PokemonCard
import com.globant.pokemontcgapp.MockedPokemonCard
import com.globant.pokemontcgapp.viewmodel.contract.PokemonCardDetailContract
import kotlinx.coroutines.launch

class PokemonCardDetailViewModel :
    ViewModel(), PokemonCardDetailContract.ViewModel {

    private val pokemonCardMutableLiveData = MutableLiveData<Data>()
    override fun getPokemonCardLiveData(): LiveData<Data> = pokemonCardMutableLiveData

    override fun getPokemonCard() = viewModelScope.launch {
        // TODO: Recover the card information from the API using a use case
        pokemonCardMutableLiveData.value = Data(Status.SUCCESS, MockedPokemonCard().pokemonCardSelected)
    }

    data class Data(var status: Status, val data: PokemonCard)

    enum class Status {
        SUCCESS
    }
}
