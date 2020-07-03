package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.pokemontcgapp.MockedPokemonTypes
import com.globant.pokemontcgapp.viewmodel.contract.PokemonTypeContract
import kotlinx.coroutines.launch

class PokemonTypeViewModel : ViewModel(), PokemonTypeContract.ViewModel {

    private val pokemonTypesMutableLiveData = MutableLiveData<PokemonTypeData>()
    override fun getPokemonTypesLiveData(): LiveData<PokemonTypeData> = pokemonTypesMutableLiveData

    override fun getPokemonTypes() = viewModelScope.launch {
        pokemonTypesMutableLiveData.value = PokemonTypeData(PokemonTypeState.POKEMON_TYPE_DATA, MockedPokemonTypes())
        // TODO: Recover the list of types from the API using a use case
    }

    data class PokemonTypeData(val state: PokemonTypeState, val data: MockedPokemonTypes? = null)
    enum class PokemonTypeState { LOADING, POKEMON_TYPE_DATA }
}
