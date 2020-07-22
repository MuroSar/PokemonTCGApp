package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entity.PokemonType
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.util.Result
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.contract.PokemonTypeContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonTypeViewModel(private val getPokemonTypesUseCase: GetPokemonTypesUseCase) : ViewModel(), PokemonTypeContract.ViewModel {

    private val pokemonTypesMutableLiveData = MutableLiveData<Event<Data>>()
    override fun getPokemonTypesLiveData(): LiveData<Event<Data>> = pokemonTypesMutableLiveData

    override fun getPokemonTypes(pokemonTypesResources: MutableMap<String, Pair<Int, Int>>) = viewModelScope.launch {
        pokemonTypesMutableLiveData.postValue(Event(Data(status = Status.LOADING)))
        withContext(Dispatchers.IO) { getPokemonTypesUseCase.invoke(pokemonTypesResources) }.let { result ->
            when (result) {
                is Result.Success -> {
                    pokemonTypesMutableLiveData.postValue(Event(Data(status = Status.SUCCESS, data = result.data)))
                }
                is Result.Failure -> {
                    pokemonTypesMutableLiveData.postValue(Event(Data(status = Status.ERROR, error = result.exception)))
                }
            }
        }
    }

    override fun onPokemonTypeSelected(typeSelected: PokemonType) {
        pokemonTypesMutableLiveData.value = Event(Data(status = Status.ON_TYPE_CLICKED, pokemonType = typeSelected))
    }

    data class Data(
        var status: Status,
        var data: List<PokemonType> = emptyList(),
        var error: Exception? = null,
        var pokemonType: PokemonType? = null
    )

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        ON_TYPE_CLICKED
    }
}
