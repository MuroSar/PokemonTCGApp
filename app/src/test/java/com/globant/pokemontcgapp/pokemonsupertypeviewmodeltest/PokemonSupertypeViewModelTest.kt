package com.globant.pokemontcgapp.pokemonsupertypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.entity.PokemonSupertype
import com.globant.domain.usecase.GetPokemonSupertypesUseCase
import com.globant.domain.util.Result
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSupertypeContract
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
class PokemonSupertypeViewModelTest {
    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext(TEST_THREAD)

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonSupertypeContract.ViewModel
    private val mockedGetPokemonSupertypesUseCase: GetPokemonSupertypesUseCase = mock()
    private val pokemonSupertypesList: List<PokemonSupertype> = mock()
    private val pokemonSupertypesResources: MutableMap<String, Int> = mock()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = PokemonSupertypeViewModel(mockedGetPokemonSupertypesUseCase)
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `on getPokemonSupertypes called successfully`() {
        val liveDataUnderTest = viewModel.getPokemonSupertypesLiveData().testObserver()
        val successResult: Result.Success<List<PokemonSupertype>> = mock()

        whenever(mockedGetPokemonSupertypesUseCase.invoke(pokemonSupertypesResources)).thenReturn(successResult)
        whenever(successResult.data).thenReturn(pokemonSupertypesList)
        runBlocking {
            viewModel.getPokemonSupertypes(pokemonSupertypesResources).join()
        }

        verify(mockedGetPokemonSupertypesUseCase).invoke(pokemonSupertypesResources)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonSupertypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonSupertypes called with error`() {
        val liveDataUnderTest = viewModel.getPokemonSupertypesLiveData().testObserver()
        val failureResult: Result.Failure = mock()
        val exception: Exception = mock()

        whenever(mockedGetPokemonSupertypesUseCase.invoke(pokemonSupertypesResources)).thenReturn(failureResult)
        whenever(failureResult.exception).thenReturn(exception)
        runBlocking {
            viewModel.getPokemonSupertypes(pokemonSupertypesResources).join()
        }
        verify(mockedGetPokemonSupertypesUseCase).invoke(pokemonSupertypesResources)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
    }

    companion object {
        private const val TEST_THREAD = "test thread"
        private const val FIRST_RESPONSE = 0
        private const val SECOND_RESPONSE = 1
    }
}
