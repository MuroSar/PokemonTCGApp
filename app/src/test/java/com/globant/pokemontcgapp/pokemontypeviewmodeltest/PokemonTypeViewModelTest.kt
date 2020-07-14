package com.globant.pokemontcgapp.pokemontypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.database.PokemonTypeDatabase
import com.globant.domain.entity.PokemonType
import com.globant.domain.service.PokemonTypesService
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.usecase.implementation.GetPokemonTypesUseCaseImpl
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
    private lateinit var getPokemonTypesUseCase: GetPokemonTypesUseCase
    private val mockedPokemonTypeDatabase: PokemonTypeDatabase = mock()
    private val mockedPokemonTypeService: PokemonTypesService = mock()
    private val pokemonTypesList: List<PokemonType> = mock()
    private val pokemonTypesResources: MutableMap<String, Pair<Int, Int>> = mock()
    private val resultIsSuccess: Result.Success<List<PokemonType>> = mock()
    private val resultIsFailure: Result.Failure = mock()
    private val exception: Exception = mock()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        getPokemonTypesUseCase = GetPokemonTypesUseCaseImpl(mockedPokemonTypeService, mockedPokemonTypeDatabase)
        viewModel = PokemonTypeViewModel(getPokemonTypesUseCase)
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

        whenever(mockedPokemonTypeService.getPokemonTypesFromAPI(pokemonTypesResources)).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonTypesList)
        runBlocking {
            viewModel.getPokemonTypes(pokemonTypesResources).join()
        }

        verify(mockedPokemonTypeService).getPokemonTypesFromAPI(pokemonTypesResources)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonTypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonTypes called with error, but request to database is successful`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()

        whenever(mockedPokemonTypeService.getPokemonTypesFromAPI(pokemonTypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonTypeDatabase.getLocalPokemonTypes()).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonTypesList)

        runBlocking {
            viewModel.getPokemonTypes(pokemonTypesResources).join()
        }
        verify(mockedPokemonTypeService).getPokemonTypesFromAPI(pokemonTypesResources)
        verify(mockedPokemonTypeDatabase).getLocalPokemonTypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonTypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonTypes called with error, request to database with error as well`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()

        whenever(mockedPokemonTypeService.getPokemonTypesFromAPI(pokemonTypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonTypeDatabase.getLocalPokemonTypes()).thenReturn(resultIsFailure)
        whenever(resultIsFailure.exception).thenReturn(exception)

        runBlocking {
            viewModel.getPokemonTypes(pokemonTypesResources).join()
        }
        verify(mockedPokemonTypeService).getPokemonTypesFromAPI(pokemonTypesResources)
        verify(mockedPokemonTypeDatabase).getLocalPokemonTypes()

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
