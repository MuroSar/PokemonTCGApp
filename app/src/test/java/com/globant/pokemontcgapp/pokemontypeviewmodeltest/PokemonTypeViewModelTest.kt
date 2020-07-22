package com.globant.pokemontcgapp.pokemontypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.database.PokemonTypeDatabase
import com.globant.domain.entity.PokemonType
import com.globant.domain.service.PokemonTypesService
import com.globant.domain.usecase.GetPokemonTypesUseCase
import com.globant.domain.usecase.implementation.GetPokemonTypesUseCaseImpl
import com.globant.domain.util.FIRST_RESPONSE
import com.globant.domain.util.Result
import com.globant.domain.util.SECOND_RESPONSE
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.util.Color
import com.globant.pokemontcgapp.util.Constant
import com.globant.pokemontcgapp.util.Drawable
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonTypeContract
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
class PokemonTypeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

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
    private val typeSelected: PokemonType = PokemonType(
        Constant.COLORLESS,
        Drawable.pokemon_colorless_type,
        Color.pokemon_type_colorless
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPokemonTypesUseCase = GetPokemonTypesUseCaseImpl(mockedPokemonTypeService, mockedPokemonTypeDatabase)
        viewModel = PokemonTypeViewModel(getPokemonTypesUseCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on getPokemonTypes called successfully`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()

        whenever(mockedPokemonTypeService.getPokemonTypes(pokemonTypesResources)).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonTypesList)
        runBlocking {
            viewModel.getPokemonTypes(pokemonTypesResources).join()
        }

        verify(mockedPokemonTypeService).getPokemonTypes(pokemonTypesResources)
        verify(mockedPokemonTypeDatabase).insertLocalPokemonTypes(pokemonTypesList)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonTypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonTypes called with error, but request to database is successful`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()

        whenever(mockedPokemonTypeService.getPokemonTypes(pokemonTypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonTypeDatabase.getLocalPokemonTypes()).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonTypesList)

        runBlocking {
            viewModel.getPokemonTypes(pokemonTypesResources).join()
        }
        verify(mockedPokemonTypeService).getPokemonTypes(pokemonTypesResources)
        verify(mockedPokemonTypeDatabase).getLocalPokemonTypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonTypesList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonTypes called with error, request to database with error as well`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()

        whenever(mockedPokemonTypeService.getPokemonTypes(pokemonTypesResources)).thenReturn(resultIsFailure)
        whenever(mockedPokemonTypeDatabase.getLocalPokemonTypes()).thenReturn(resultIsFailure)
        whenever(resultIsFailure.exception).thenReturn(exception)

        runBlocking {
            viewModel.getPokemonTypes(pokemonTypesResources).join()
        }
        verify(mockedPokemonTypeService).getPokemonTypes(pokemonTypesResources)
        verify(mockedPokemonTypeDatabase).getLocalPokemonTypes()

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(resultIsFailure.exception, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.error)
    }

    @Test
    fun `on onPokemonTypeSelected called`() {
        val liveDataUnderTest = viewModel.getPokemonTypesLiveData().testObserver()

        viewModel.onPokemonTypeSelected(typeSelected)

        assertEquals(Status.ON_TYPE_CLICKED, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(typeSelected, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.pokemonType)
    }
}
