package com.globant.pokemontcgapp.pokemonsupertypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.database.PokemonSupertypeDatabase
import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.service.PokemonSupertypesService
import com.globant.domain.usecase.GetPokemonSupertypesUseCase
import com.globant.domain.usecase.implementation.GetPokemonSupertypesUseCaseImpl
import com.globant.domain.util.FIRST_RESPONSE
import com.globant.domain.util.Result
import com.globant.domain.util.SECOND_RESPONSE
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSupertypeContract
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonSupertypeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonSupertypeContract.ViewModel
    private lateinit var getPokemonSupertypesUseCase: GetPokemonSupertypesUseCase
    private val mockedPokemonSupertypeDatabase: PokemonSupertypeDatabase = mock()
    private val mockedPokemonSupertypeService: PokemonSupertypesService = mock()
    private val pokemonSupertypesList: List<SecondaryTypes> = mock()
    private val pokemonSupertypesResources: MutableMap<String, Int> = mock()
    private val resultIsSuccess: Result.Success<List<SecondaryTypes>> = mock()
    private val resultIsFailure: Result.Failure = mock()
    private val exception: Exception = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPokemonSupertypesUseCase = GetPokemonSupertypesUseCaseImpl(mockedPokemonSupertypeService, mockedPokemonSupertypeDatabase)
        viewModel = PokemonSupertypeViewModel(getPokemonSupertypesUseCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on getPokemonSupertypes called successfully`() {
        val liveDataUnderTest = viewModel.getPokemonSupertypesLiveData().testObserver()

        whenever(mockedPokemonSupertypeService.getPokemonSupertypes(pokemonSupertypesResources)).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonSupertypesList)
        runBlocking {
            viewModel.getPokemonSupertypes(pokemonSupertypesResources).join()
        }

        verify(mockedPokemonSupertypeService).getPokemonSupertypes(pokemonSupertypesResources)
        verify(mockedPokemonSupertypeDatabase).insertLocalPokemonSupertypes(pokemonSupertypesList)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonSupertypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonSupertypes called with error, but request to database is successful`() {
        val liveDataUnderTest = viewModel.getPokemonSupertypesLiveData().testObserver()

        whenever(mockedPokemonSupertypeService.getPokemonSupertypes(pokemonSupertypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonSupertypeDatabase.getLocalPokemonSupertypes()).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonSupertypesList)

        runBlocking {
            viewModel.getPokemonSupertypes(pokemonSupertypesResources).join()
        }
        verify(mockedPokemonSupertypeService).getPokemonSupertypes(pokemonSupertypesResources)
        verify(mockedPokemonSupertypeDatabase).getLocalPokemonSupertypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonSupertypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonSupertypes called with error, request to database with error as well`() {
        val liveDataUnderTest = viewModel.getPokemonSupertypesLiveData().testObserver()

        whenever(mockedPokemonSupertypeService.getPokemonSupertypes(pokemonSupertypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonSupertypeDatabase.getLocalPokemonSupertypes()).thenReturn(resultIsFailure)
        whenever(resultIsFailure.exception).thenReturn(exception)

        runBlocking {
            viewModel.getPokemonSupertypes(pokemonSupertypesResources).join()
        }
        verify(mockedPokemonSupertypeService).getPokemonSupertypes(pokemonSupertypesResources)
        verify(mockedPokemonSupertypeDatabase).getLocalPokemonSupertypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(resultIsFailure.exception, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.error)
    }
}
