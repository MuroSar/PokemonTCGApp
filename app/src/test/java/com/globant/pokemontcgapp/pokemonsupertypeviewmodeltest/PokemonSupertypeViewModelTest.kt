package com.globant.pokemontcgapp.pokemonsupertypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.entity.PokemonSupertype
import com.globant.pokemontcgapp.MockedPokemonSupertypes
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSupertypeContract
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
class PokemonSupertypeViewModelTest {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext(TEST_THREAD)

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonSupertypeContract.ViewModel
    private val pokemonSupertypesList: List<PokemonSupertype> = MockedPokemonSupertypes().pokemonSupertypesList

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = PokemonSupertypeViewModel()
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
        val liveDataUnderTest = viewModel.getPokemonSupertypesLiveData().testObserver()

        runBlocking {
            viewModel.getPokemonSupertypes().join()
        }

        Assert.assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[ZERO]?.status)
        Assert.assertEquals(
            pokemonSupertypesList[ZERO].name,
            liveDataUnderTest.observedValues[ZERO]?.data?.pokemonSupertypesList?.get(ZERO)?.name
        )
    }

    companion object {
        private const val TEST_THREAD = "test thread"
        private const val ZERO = 0
    }
}
