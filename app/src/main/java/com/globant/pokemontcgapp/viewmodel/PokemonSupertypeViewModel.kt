package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.usecase.GetPokemonSupertypesUseCase
import com.globant.domain.util.Result
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSupertypeContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonSupertypeViewModel(private val getPokemonSupertypesUseCase: GetPokemonSupertypesUseCase) :
    ViewModel(),
    PokemonSupertypeContract.ViewModel {

    private val pokemonSupertypesMutableLiveData = MutableLiveData<Event<Data<List<SecondaryTypes>>>>()
    override fun getPokemonSupertypesLiveData(): LiveData<Event<Data<List<SecondaryTypes>>>> = pokemonSupertypesMutableLiveData

    override fun getPokemonSupertypes(pokemonSupertypesResources: MutableMap<String, Int>) = viewModelScope.launch {
        pokemonSupertypesMutableLiveData.postValue(Event(Data(status = Status.LOADING)))
        withContext(Dispatchers.IO) { getPokemonSupertypesUseCase.invoke(pokemonSupertypesResources) }.let { result ->
            when (result) {
                is Result.Success -> {
                    pokemonSupertypesMutableLiveData.postValue(Event(Data(status = Status.SUCCESS, data = result.data)))
                }
                is Result.Failure -> {
                    pokemonSupertypesMutableLiveData.postValue(Event(Data(status = Status.ERROR, error = result.exception)))
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
