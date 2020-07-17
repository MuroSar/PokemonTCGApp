package com.globant.pokemontcgapp.pokemonsubtypeviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.entity.PokemonSubtype
import com.globant.pokemontcgapp.MockedPokemonSubtypes
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel.Status
import com.globant.pokemontcgapp.viewmodel.contract.PokemonSubtypeContract
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
class PokemonSubTypeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonSubtypeContract.ViewModel
    private val pokemonSubtypesList: List<PokemonSubtype> = MockedPokemonSubtypes().pokemonSubtypesList

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PokemonSubtypeViewModel()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on getPokemonSubtypes called`() {
        val liveDataUnderTest = viewModel.getPokemonSubtypesLiveData().testObserver()

        runBlocking {
            viewModel.getPokemonSubtypes().join()
        }

        assertEquals(Status.SUCCESS, liveDataUnderTest.observedValues[ZERO]?.status)
        assertEquals(
            pokemonSubtypesList[ZERO].name,
            liveDataUnderTest.observedValues[ZERO]?.data?.pokemonSubtypesList?.get(ZERO)?.name
        )
    }

    companion object {
        private const val ZERO = 0
    }
}
