package com.globant.pokemontcgapp.pokemonsubtypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.database.PokemonSubtypeDatabase
import com.globant.domain.entity.SecondaryTypes
import com.globant.domain.service.PokemonSubtypesService
import com.globant.domain.usecase.GetPokemonSubtypesUseCase
import com.globant.domain.usecase.implementation.GetPokemonSubtypesUseCaseImpl
import com.globant.domain.util.Result
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSubtypeContract
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
class PokemonSubtypeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonSubtypeContract.ViewModel
    private lateinit var getPokemonSubtypesUseCase: GetPokemonSubtypesUseCase
    private val mockedPokemonSubtypeDatabase: PokemonSubtypeDatabase = mock()
    private val mockedPokemonSubtypeService: PokemonSubtypesService = mock()
    private val pokemonSubtypesList: List<SecondaryTypes> = mock()
    private val pokemonSubtypesResources: MutableMap<String, Int> = mock()
    private val resultIsSuccess: Result.Success<List<SecondaryTypes>> = mock()
    private val resultIsFailure: Result.Failure = mock()
    private val exception: Exception = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPokemonSubtypesUseCase = GetPokemonSubtypesUseCaseImpl(mockedPokemonSubtypeService, mockedPokemonSubtypeDatabase)
        viewModel = PokemonSubtypeViewModel(getPokemonSubtypesUseCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on getPokemonSubtypes called successfully`() {
        val liveDataUnderTest = viewModel.getPokemonSubtypesLiveData().testObserver()

        whenever(mockedPokemonSubtypeService.getPokemonSubtypes(pokemonSubtypesResources)).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonSubtypesList)
        runBlocking {
            viewModel.getPokemonSubtypes(pokemonSubtypesResources).join()
        }

        verify(mockedPokemonSubtypeService).getPokemonSubtypes(pokemonSubtypesResources)
        verify(mockedPokemonSubtypeDatabase).insertLocalPokemonSubtypes(pokemonSubtypesList)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonSubtypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonSubtypes called with error, but request to database is successful`() {
        val liveDataUnderTest = viewModel.getPokemonSubtypesLiveData().testObserver()

        whenever(mockedPokemonSubtypeService.getPokemonSubtypes(pokemonSubtypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonSubtypeDatabase.getLocalPokemonSubtypes()).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonSubtypesList)

        runBlocking {
            viewModel.getPokemonSubtypes(pokemonSubtypesResources).join()
        }
        verify(mockedPokemonSubtypeService).getPokemonSubtypes(pokemonSubtypesResources)
        verify(mockedPokemonSubtypeDatabase).getLocalPokemonSubtypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonSubtypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonSubtypes called with error, request to database with error as well`() {
        val liveDataUnderTest = viewModel.getPokemonSubtypesLiveData().testObserver()

        whenever(mockedPokemonSubtypeService.getPokemonSubtypes(pokemonSubtypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonSubtypeDatabase.getLocalPokemonSubtypes()).thenReturn(resultIsFailure)
        whenever(resultIsFailure.exception).thenReturn(exception)

        runBlocking {
            viewModel.getPokemonSubtypes(pokemonSubtypesResources).join()
        }
        verify(mockedPokemonSubtypeService).getPokemonSubtypes(pokemonSubtypesResources)
        verify(mockedPokemonSubtypeDatabase).getLocalPokemonSubtypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(resultIsFailure.exception, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.error)
    }

    companion object {
        private const val FIRST_RESPONSE = 0
        private const val SECOND_RESPONSE = 1
    }
}
