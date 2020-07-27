package com.globant.pokemontcgapp.splashscreenviewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.util.FIRST_RESPONSE
import com.globant.domain.util.SECOND_RESPONSE
import com.globant.pokemontcgapp.testObserver
import com.globant.pokemontcgapp.viewmodel.SplashScreenViewModel
import com.globant.pokemontcgapp.viewmodel.SplashScreenViewModel.SplashScreenStatus
import com.globant.pokemontcgapp.viewmodel.contract.SplashScreenContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
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
class SplashScreenViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SplashScreenContract.ViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SplashScreenViewModel()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on initSplashScreen called`() {
        val liveDataUnderTest = viewModel.initSplashScreenLiveData().testObserver()

        runBlockingTest(testDispatcher) {
            viewModel.initSplashScreen().join()
        }

        assertEquals(SplashScreenStatus.INIT, liveDataUnderTest.observedValues[FIRST_RESPONSE])
        assertEquals(SplashScreenStatus.FINISH, liveDataUnderTest.observedValues[SECOND_RESPONSE])
    }
}
