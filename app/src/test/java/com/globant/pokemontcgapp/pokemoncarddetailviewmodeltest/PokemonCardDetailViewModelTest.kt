package com.globant.pokemontcgapp.pokemoncarddetailviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.entity.PokemonCard
import com.globant.domain.service.PokemonCardDetailService
import com.globant.domain.usecase.GetPokemonCardDetailUseCase
import com.globant.domain.usecase.implementation.GetPokemonCardDetailUseCaseImpl
import com.globant.domain.util.FIRST_RESPONSE
import com.globant.domain.util.Result
import com.globant.domain.util.SECOND_RESPONSE
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonCardDetailContract
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
class PokemonCardDetailViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonCardDetailContract.ViewModel
    private lateinit var getPokemonCardDetailUseCase: GetPokemonCardDetailUseCase
    private val mockedPokemonCardDetailService: PokemonCardDetailService = mock()
    private val pokemonCard: PokemonCard = mock()
    private val resultIsSuccess: Result.Success<PokemonCard> = mock()
    private val resultIsFailure: Result.Failure = mock()
    private val exception: Exception = mock()
    private val pokemonCardId: String = ID

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPokemonCardDetailUseCase = GetPokemonCardDetailUseCaseImpl(mockedPokemonCardDetailService)
        viewModel = PokemonCardDetailViewModel(getPokemonCardDetailUseCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on getPokemonCardDetail called successfully`() {
        val liveDataUnderTest = viewModel.getPokemonCardLiveData().testObserver()

        whenever(getPokemonCardDetailUseCase.invoke(pokemonCardId)).thenReturn(resultIsSuccess)
        whenever(resultIsSuccess.data).thenReturn(pokemonCard)
        runBlocking {
            viewModel.getPokemonCard(pokemonCardId).join()
        }

        verify(mockedPokemonCardDetailService).getPokemonCardDetail(pokemonCardId)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
        assertEquals(pokemonCard, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.data)
    }

    @Test
    fun `on getPokemonCardDetail called with error`() {
        val liveDataUnderTest = viewModel.getPokemonCardLiveData().testObserver()

        whenever(getPokemonCardDetailUseCase.invoke(pokemonCardId)).thenReturn(resultIsFailure)
        whenever(resultIsFailure.exception).thenReturn(exception)
        runBlocking {
            viewModel.getPokemonCard(pokemonCardId).join()
        }
        verify(mockedPokemonCardDetailService).getPokemonCardDetail(pokemonCardId)

        assertEquals(Status.LOADING, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.peekContent()?.status)
        assertEquals(Status.ERROR, liveDataUnderTest.observedValues[SECOND_RESPONSE]?.peekContent()?.status)
    }

    companion object {
        private const val ID = "xy0-25"
    }
}
