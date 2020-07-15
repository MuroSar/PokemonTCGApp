package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.pokemontcgapp.MockedPokemonSupertypes
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSupertypeContract
import kotlinx.coroutines.launch

class PokemonSupertypeViewModel : ViewModel(), PokemonSupertypeContract.ViewModel {

    private val pokemonSupertypesMutableLiveData = MutableLiveData<Data>()
    override fun getPokemonSupertypesLiveData(): LiveData<Data> = pokemonSupertypesMutableLiveData

    override fun getPokemonSupertypes() = viewModelScope.launch {
        pokemonSupertypesMutableLiveData.value = Data(Status.SUCCESS, MockedPokemonSupertypes())
        // TODO: Recover the list of types from the API using a use case
    }

    data class Data(var status: Status, val data: MockedPokemonSupertypes? = null)

    enum class Status {
        SUCCESS
    }
}
