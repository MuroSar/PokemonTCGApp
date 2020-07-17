package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.pokemontcgapp.MockedPokemonSubtypes
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSubtypeContract
import kotlinx.coroutines.launch

class PokemonSubtypeViewModel : ViewModel(), PokemonSubtypeContract.ViewModel {

    private val pokemonSubtypesMutableLiveData = MutableLiveData<Data>()
    override fun getPokemonSubtypesLiveData(): LiveData<Data> = pokemonSubtypesMutableLiveData

    override fun getPokemonSubtypes() = viewModelScope.launch {
        // TODO: Recover the list of types from the API using a use case
        pokemonSubtypesMutableLiveData.value = Data(Status.SUCCESS, MockedPokemonSubtypes())
    }

    data class Data(var status: Status, val data: MockedPokemonSubtypes? = null)

    enum class Status {
        SUCCESS
    }
}
