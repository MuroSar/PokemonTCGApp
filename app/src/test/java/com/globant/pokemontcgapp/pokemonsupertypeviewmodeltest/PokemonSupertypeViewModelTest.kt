package com.globant.pokemontcgapp.pokemonsupertypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.database.PokemonSupertypeDatabase
import com.globant.domain.entity.PokemonSupertype
import com.globant.domain.service.PokemonSupertypesService
import com.globant.domain.usecase.GetPokemonSupertypesUseCase
import com.globant.domain.usecase.implementation.GetPokemonSupertypesUseCaseImpl
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
    private lateinit var getPokemonSupertypesUseCase: GetPokemonSupertypesUseCase
    private val mockedPokemonSupertypeDatabase: PokemonSupertypeDatabase = mock()
    private val mockedPokemonSupertypeService: PokemonSupertypesService = mock()
    private val pokemonSupertypesList: List<PokemonSupertype> = mock()
    private val pokemonSupertypesResources: MutableMap<String, Int> = mock()
    private val resultIsSuccess: Result.Success<List<PokemonSupertype>> = mock()
    private val resultIsFailure: Result.Failure = mock()
    private val exception: Exception = mock()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        getPokemonSupertypesUseCase = GetPokemonSupertypesUseCaseImpl(mockedPokemonSupertypeService, mockedPokemonSupertypeDatabase)
        viewModel = PokemonSupertypeViewModel(getPokemonSupertypesUseCase)
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

        whenever(mockedPokemonSupertypeService.getPokemonSupertypesFromAPI(pokemonSupertypesResources)).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonSupertypesList)
        runBlocking {
            viewModel.getPokemonSupertypes(pokemonSupertypesResources).join()
        }

        verify(mockedPokemonSupertypeService).getPokemonSupertypesFromAPI(pokemonSupertypesResources)
        verify(mockedPokemonSupertypeDatabase).insertLocalPokemonSupertypes(pokemonSupertypesList)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonSupertypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonSupertypes called with error, but request to database is successful`() {
        val liveDataUnderTest = viewModel.getPokemonSupertypesLiveData().testObserver()

        whenever(mockedPokemonSupertypeService.getPokemonSupertypesFromAPI(pokemonSupertypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonSupertypeDatabase.getLocalPokemonSupertypes()).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonSupertypesList)

        runBlocking {
            viewModel.getPokemonSupertypes(pokemonSupertypesResources).join()
        }
        verify(mockedPokemonSupertypeService).getPokemonSupertypesFromAPI(pokemonSupertypesResources)
        verify(mockedPokemonSupertypeDatabase).getLocalPokemonSupertypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonSupertypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonSupertypes called with error, request to database with error as well`() {
        val liveDataUnderTest = viewModel.getPokemonSupertypesLiveData().testObserver()

        whenever(mockedPokemonSupertypeService.getPokemonSupertypesFromAPI(pokemonSupertypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonSupertypeDatabase.getLocalPokemonSupertypes()).thenReturn(resultIsFailure)
        whenever(resultIsFailure.exception).thenReturn(exception)

        runBlocking {
            viewModel.getPokemonSupertypes(pokemonSupertypesResources).join()
        }
        verify(mockedPokemonSupertypeService).getPokemonSupertypesFromAPI(pokemonSupertypesResources)
        verify(mockedPokemonSupertypeDatabase).getLocalPokemonSupertypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(resultIsFailure.exception, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.error)
    }

    companion object {
        private const val TEST_THREAD = "test thread"
        private const val FIRST_RESPONSE = 0
        private const val SECOND_RESPONSE = 1
    }
}
