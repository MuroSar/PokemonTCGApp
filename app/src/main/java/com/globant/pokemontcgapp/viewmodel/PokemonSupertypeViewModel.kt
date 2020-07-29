package com.globant.pokemontcgapp.viewmodel

import android.view.View
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

    private val pokemonSupertypesMutableLiveData = MutableLiveData<Event<Data>>()
    override fun getPokemonSupertypesLiveData(): LiveData<Event<Data>> = pokemonSupertypesMutableLiveData

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

    override fun onPokemonSupertypeSelected(supertypeSelected: SecondaryTypes, sharedView: View) {
        pokemonSupertypesMutableLiveData.value = Event(
            Data(
                status = Status.ON_SUPERTYPE_CLICKED,
                pokemonSupertype = supertypeSelected,
                sharedView = sharedView
            )
        )
    }

    data class Data(
        var status: Status,
        var data: List<SecondaryTypes> = emptyList(),
        var sharedView: View? = null,
        var error: Exception? = null,
        var pokemonSupertype: SecondaryTypes? = null
    )

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        ON_SUPERTYPE_CLICKED
    }
}
