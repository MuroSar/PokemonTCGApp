package com.globant.pokemontcgapp.pokemoncarddetailviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.util.FIRST_RESPONSE
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.util.Constant
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonCardDetailContract
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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PokemonCardDetailViewModel()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on getPokemonCard called`() {
        val liveDataUnderTest = viewModel.getPokemonCardLiveData().testObserver()

        runBlocking {
            viewModel.getPokemonCard().join()
        }

        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[FIRST_RESPONSE]?.status)
        assertEquals(
            Constant.pokemonCard.name,
            liveDataUnderTest.observedValues[FIRST_RESPONSE]?.data?.name
        )
    }
}
