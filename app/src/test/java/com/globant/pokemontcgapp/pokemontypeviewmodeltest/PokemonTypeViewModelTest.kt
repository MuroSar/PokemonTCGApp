package com.globant.pokemontcgapp.pokemontypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.entity.PokemonType
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.util.Result
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonTypeContract
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
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
    private val mockedGetPokemonTypesUseCase: GetPokemonTypesUseCase = mock()
    private val pokemonTypesList: List<PokemonType> = mock()
    private val pokemonTypesResources: MutableMap<String, Pair<Int, Int>> = mock()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = PokemonTypeViewModel(mockedGetPokemonTypesUseCase)
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `on getPokemonTypes called successfully`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()
        val successResult: Result.Success<List<PokemonType>> = mock()

        whenever(mockedGetPokemonTypesUseCase.invoke(pokemonTypesResources)).thenReturn(successResult)
        whenever(successResult.data).thenReturn(pokemonTypesList)
        runBlocking {
            viewModel.getPokemonTypes(pokemonTypesResources).join()
        }

        verify(mockedGetPokemonTypesUseCase).invoke(pokemonTypesResources)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonTypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonTypes called with error`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()
        val failureResult: Result.Failure = mock()
        val exception: Exception = mock()

        whenever(mockedGetPokemonTypesUseCase.invoke(pokemonTypesResources)).thenReturn(failureResult)
        whenever(failureResult.exception).thenReturn(exception)
        runBlocking {
            viewModel.getPokemonTypes(pokemonTypesResources).join()
        }
        verify(mockedGetPokemonTypesUseCase).invoke(pokemonTypesResources)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
    }

    companion object {
        private const val TEST_THREAD = "test thread"
        private const val FIRST_RESPONSE = 0
        private const val SECOND_RESPONSE = 1
    }
}
