package com.globant.pokemontcgapp.pokemontypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.pokemontcgapp.MockedPokemonTypes
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.util.PokemonTypeState
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel
import com.globant.pokemontcgapp.viewmodel.contract.PokemonTypeContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonTypeViewModelTest {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext(TEST_THREAD)

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonTypeContract.ViewModel
    private val pokemonTypesList: List<String> = MockedPokemonTypes().pokemonTypesList

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = PokemonTypeViewModel()
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `on getPokemonTypes called`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()

        runBlocking {
            viewModel.getPokemonTypes().join()
        }

        Assert.assertEquals(PokemonTypeState.POKEMON_TYPE_DATA, liveDataUnderTest.observedValues[ZERO]?.state)
        Assert.assertEquals(pokemonTypesList, liveDataUnderTest.observedValues[ZERO]?.data?.pokemonTypesList)
    }

    companion object {
        private const val TEST_THREAD = "test thread"
        private const val ZERO = 0
    }
}
