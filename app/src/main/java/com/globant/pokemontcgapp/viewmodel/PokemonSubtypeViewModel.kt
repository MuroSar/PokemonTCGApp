package com.globant.pokemontcgapp.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.usecase.GetPokemonSubtypesUseCase
import com.globant.domain.util.Result
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSubtypeContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonSubtypeViewModel(private val getPokemonSubtypesUseCase: GetPokemonSubtypesUseCase) :
    ViewModel(),
    PokemonSubtypeContract.ViewModel {

    private val pokemonSubtypesMutableLiveData = MutableLiveData<Event<Data>>()
    override fun getPokemonSubtypesLiveData(): LiveData<Event<Data>> = pokemonSubtypesMutableLiveData

    override fun getPokemonSubtypes(pokemonSubtypesResources: MutableMap<String, Int>) = viewModelScope.launch {
        pokemonSubtypesMutableLiveData.postValue(Event(Data(status = Status.LOADING)))
        withContext(Dispatchers.IO) { getPokemonSubtypesUseCase.invoke(pokemonSubtypesResources) }.let { result ->
            when (result) {
                is Result.Success -> {
                    pokemonSubtypesMutableLiveData.postValue(Event(Data(status = Status.SUCCESS, data = result.data)))
                }
                is Result.Failure -> {
                    pokemonSubtypesMutableLiveData.postValue(Event(Data(status = Status.ERROR, error = result.exception)))
                }
            }
        }
    }

    override fun onPokemonSubtypeSelected(subtypeSelected: SecondaryTypes, sharedView: View) {
        pokemonSubtypesMutableLiveData.value = Event(
            Data(
                status = Status.ON_SUBTYPE_CLICKED,
                pokemonSubtype = subtypeSelected,
                sharedView = sharedView
            )
        )
    }

    data class Data(
        var status: Status,
        var data: List<SecondaryTypes> = emptyList(),
        var sharedView: View? = null,
        var error: Exception? = null,
        var pokemonSubtype: SecondaryTypes? = null
    )

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        ON_SUBTYPE_CLICKED
    }
}
