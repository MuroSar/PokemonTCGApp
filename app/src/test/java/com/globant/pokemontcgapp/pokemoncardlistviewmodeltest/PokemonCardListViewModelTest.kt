package com.globant.pokemontcgapp.pokemoncardlistviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardListService
import com.globant.domain.usecase.GetPokemonCardListUseCase
import com.globant.domain.usecase.implementation.GetPokemonCardListUseCaseImpl
import com.globant.domain.util.Result
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
    private val mockedPokemonCardListService: PokemonCardListService = mock()
    private val pokemonCardList: List<PokemonCard> = mock()
    private val resultIsSuccess: Result.Success<List<PokemonCard>> = mock()
    private val resultIsFailure: Result.Failure = mock()
    private val exception: Exception = mock()
    private val pokemonCardGroup: String = TYPE
    private val pokemonCardGroupSelected: String = COLORLESS

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPokemonCardListUseCase = GetPokemonCardListUseCaseImpl(mockedPokemonCardListService)
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

        whenever(getPokemonCardListUseCase.invoke(pokemonCardGroup, pokemonCardGroupSelected)).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonCardList)
        runBlocking {
            viewModel.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected).join()
        }

        verify(mockedPokemonCardListService).getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonCardList, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonCardList called with error`() {
        val liveDataUnderTest = viewModel.getPokemonCardListLiveData().testObserver()

        whenever(getPokemonCardListUseCase.invoke(pokemonCardGroup, pokemonCardGroupSelected)).thenReturn(resultIsFailure)
        whenever(resultIsFailure.exception).thenReturn(exception)
        runBlocking {
            viewModel.getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected).join()
        }
        verify(mockedPokemonCardListService).getPokemonCardList(pokemonCardGroup, pokemonCardGroupSelected)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
    }

    companion object {
        private const val FIRST_RESPONSE = 0
        private const val SECOND_RESPONSE = 1
        private const val TYPE = "type"
        private const val COLORLESS = "Colorless"
    }
}
