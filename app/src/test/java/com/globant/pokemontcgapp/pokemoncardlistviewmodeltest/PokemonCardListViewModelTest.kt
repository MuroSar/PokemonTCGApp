package com.globant.pokemontcgapp.pokemoncardlistviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.entity.PokemonCard
import com.globant.pokemontcgapp.MockedPokemonCardList
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonCardListContract
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
    private val pokemonCardListList: List<PokemonCard> = MockedPokemonCardList().pokemonCardList

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PokemonCardListViewModel()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on getPokemonCardList called`() {
        val liveDataUnderTest = viewModel.getPokemonCardListLiveData().testObserver()

        runBlocking {
            viewModel.getPokemonCardList().join()
        }

        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[ZERO]?.status)
        assertEquals(
            pokemonCardListList[ZERO].name,
            liveDataUnderTest.observedValues[ZERO]?.data?.pokemonCardList?.get(ZERO)?.name
        )
    }

    companion object {
        private const val ZERO = 0
    }
}
