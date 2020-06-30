package com.globant.pokemontcgapp.splashscreentest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.globant.pokemontcgapp.contract.SplashScreenContract
import com.globant.pokemontcgapp.viewmodel.SplashScreenViewModel
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
class SplashScreenViewModelTest {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext(UI_THREAD)

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SplashScreenContract.ViewModel

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = SplashScreenViewModel()
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `on initSplashScreen called`() {
        val liveDataUnderTest = viewModel.initSplashScreenLiveData().testObserver()

        runBlocking {
            viewModel.initSplashScreen().join()
        }

        assertEquals(SplashScreenViewModel.SplashScreenStatus.INIT, liveDataUnderTest.observedValues[ZERO])
        assertEquals(SplashScreenViewModel.SplashScreenStatus.FINISH, liveDataUnderTest.observedValues[ONE])
    }

    class TestObserver<T> : Observer<T> {

        val observedValues = mutableListOf<T?>()

        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also { observeForever(it) }

    companion object {
        private const val UI_THREAD = "UI thread"
        private const val ZERO = 0
        private const val ONE = 1
    }
}
