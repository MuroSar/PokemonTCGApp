package com.globant.pokemontcgapp.pokemoncardlistviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.database.PokemonCardDatabase
import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardListService
import com.globant.domain.usecase.GetPokemonCardListUseCase
import com.globant.domain.usecase.implementation.GetPokemonCardListUseCaseImpl
import com.globant.domain.util.FIRST_RESPONSE
import com.globant.domain.util.Result
import com.globant.domain.util.SECOND_RESPONSE
import com.globant.pokemontcgapp.MockedPokemonCard
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonCardListContract
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
class PokemonCardListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonCardListContract.ViewModel
    private lateinit var getPokemonCardListUseCase: GetPokemonCardListUseCase
    private val mockedPokemonCardListDatabase: PokemonCardDatabase = mock()
    private val mockedPokemonCardListService: PokemonCardListService = mock()
    private val pokemonCardList: List<PokemonCard> = mock()
    private val resultIsSuccess: Result.Success<List<PokemonCard>> = mock()
    private val resultIsFailure: Result.Failure = mock()
    private val exception: Exception = mock()
    private val pokemonCardGroup: String = TYPE
    private val pokemonCardGroupSelected: String = COLORLESS
    private val pokemonCardSelected: PokemonCard = MockedPokemonCard().pokemonCardSelected

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPokemonCardListUseCase = GetPokemonCardListUseCaseImpl(mockedPokemonCardListService, mockedPokemonCardListDatabase)
        viewModel = PokemonCardListViewModel(getPokemonCardListUseCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on getPokemonCardList called successfully`() {
        val liveDataUnderTest = viewModel.getPokemonCardListLiveData().testObserver()

        whenever(mockedPokemonCardListService.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonCardList)
        runBlocking {
            viewModel.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected).join()
        }

        verify(mockedPokemonCardListService).getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)
        verify(mockedPokemonCardListDatabase).insertLocalPokemonCardList(pokemonCardList)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonCardList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonCardList called with error, but request to database is successful`() {
        val liveDataUnderTest = viewModel.getPokemonCardListLiveData().testObserver()

        whenever(mockedPokemonCardListService.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)).thenReturn(resultIsFailure)
        whenever(mockedPokemonCardListDatabase.getLocalPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)).thenReturn(
            resultIsSuccess
        )
        whenever(resultIsSuccess.data).thenReturn(pokemonCardList)

        runBlocking {
            viewModel.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected).join()
        }
        verify(mockedPokemonCardListService).getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)
        verify(mockedPokemonCardListDatabase).getLocalPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonCardList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonCardList called with error, request to database with error as well`() {
        val liveDataUnderTest = viewModel.getPokemonCardListLiveData().testObserver()

        whenever(mockedPokemonCardListService.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)).thenReturn(resultIsFailure)
        whenever(mockedPokemonCardListDatabase.getLocalPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected))
            .thenReturn(resultIsFailure)
        whenever(resultIsFailure.exception).thenReturn(exception)

        runBlocking {
            viewModel.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected).join()
        }
        verify(mockedPokemonCardListService).getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)
        verify(mockedPokemonCardListDatabase).getLocalPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(resultIsFailure.exception, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.error)
    }

    @Test
    fun `on onPokemonCardSelected called`() {
        val liveDataUnderTest = viewModel.getPokemonCardListLiveData().testObserver()

        viewModel.onPokemonCardSelected(pokemonCardSelected)

        assertEquals(
            Status.ON_CARD_CLICKED,
            liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status
        )
        assertEquals(pokemonCardSelected, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.pokemonCard)
    }

    companion object {
        private const val TYPE = "types"
        private const val COLORLESS = "Colorless"
    }
}
