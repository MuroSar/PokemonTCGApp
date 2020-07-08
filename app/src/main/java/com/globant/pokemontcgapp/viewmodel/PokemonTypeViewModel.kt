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

    private val pokemonTypesMutableLiveData = MutableLiveData<Event<Data<List<PokemonType>>>>()
    override fun getPokemonTypesLiveData(): LiveData<Event<Data<List<PokemonType>>>> = pokemonTypesMutableLiveData

    override fun getPokemonTypes(listOfPokemonTypesResources: List<Pair<Int, Int>>) = viewModelScope.launch {
        pokemonTypesMutableLiveData.postValue(Event(Data(status = Status.LOADING)))
        withContext(Dispatchers.IO) { getPokemonTypesUseCase.invoke(listOfPokemonTypesResources) }.let { result ->
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

    data class Data<RequestData>(var status: Status, var data: RequestData? = null, var error: Exception? = null)

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
