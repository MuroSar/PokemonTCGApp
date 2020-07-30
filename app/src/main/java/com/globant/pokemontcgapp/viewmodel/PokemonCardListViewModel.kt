package com.globant.pokemontcgapp.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entity.PokemonCard
import com.globant.domain.usecase.GetPokemonCardListUseCase
import com.globant.domain.util.Result
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.viewmodel.contract.PokemonCardListContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonCardListViewModel(private val getPokemonCardListUseCase: GetPokemonCardListUseCase) :
    ViewModel(), PokemonCardListContract.ViewModel {

    private val pokemonCardListMutableLiveData = MutableLiveData<Event<Data>>()
    override fun getPokemonCardListLiveData(): LiveData<Event<Data>> = pokemonCardListMutableLiveData

    override fun getPokemonCardList(pokemonCardGroup: String, pokemonCardGroupSelected: String) = viewModelScope.launch {
        pokemonCardListMutableLiveData.postValue(Event(Data(status = Status.LOADING)))
        withContext(Dispatchers.IO) { getPokemonCardListUseCase.invoke(pokemonCardGroup, pokemonCardGroupSelected) }.let { result ->
            when (result) {
                is Result.Success -> {
                    pokemonCardListMutableLiveData.postValue(Event(Data(status = Status.SUCCESS, data = result.data)))
                }
                is Result.Failure -> {
                    pokemonCardListMutableLiveData.postValue(Event(Data(status = Status.ERROR, error = result.exception)))
                }
            }
        }
    }

    override fun onPokemonCardSelected(cardSelected: PokemonCard, sharedView: View) {
        pokemonCardListMutableLiveData.value = Event(
            Data(
                status = Status.ON_CARD_CLICKED,
                pokemonCard = cardSelected,
                sharedView = sharedView
            )
        )
    }

    data class Data(
        var status: Status,
        var data: List<PokemonCard> = emptyList(),
        var sharedView: View? = null,
        var error: Exception? = null,
        var pokemonCard: PokemonCard? = null
    )

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        ON_CARD_CLICKED
    }
}
