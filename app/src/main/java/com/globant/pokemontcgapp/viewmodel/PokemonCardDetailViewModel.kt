package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entity.PokemonCard
import com.globant.domain.usecase.GetPokemonCardDetailUseCase
import com.globant.domain.util.Result
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.contract.PokemonCardDetailContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonCardDetailViewModel(private val getPokemonCardDetailUseCase: GetPokemonCardDetailUseCase) :
    ViewModel(), PokemonCardDetailContract.ViewModel {

    private val pokemonCardMutableLiveData = MutableLiveData<Event<Data>>()
    override fun getPokemonCardLiveData(): LiveData<Event<Data>> = pokemonCardMutableLiveData

    override fun getPokemonCard(pokemonCardId: String) = viewModelScope.launch {
        pokemonCardMutableLiveData.postValue(Event(Data(status = Status.LOADING)))
        withContext(Dispatchers.IO) { getPokemonCardDetailUseCase.invoke(pokemonCardId) }.let { result ->
            when (result) {
                is Result.Success -> {
                    pokemonCardMutableLiveData.postValue(Event(Data(status = Status.SUCCESS, data = result.data)))
                }
                is Result.Failure -> {
                    pokemonCardMutableLiveData.postValue(Event(Data(status = Status.ERROR, error = result.exception)))
                }
            }
        }
    }

    data class Data(var status: Status, var data: PokemonCard? = null, var error: Exception? = null)

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
