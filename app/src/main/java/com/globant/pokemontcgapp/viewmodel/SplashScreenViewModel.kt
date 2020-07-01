package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.pokemontcgapp.viewmodel.contract.SplashScreenContract
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenViewModel : ViewModel(), SplashScreenContract.ViewModel {

    private var mutableMainState: MutableLiveData<SplashScreenStatus> = MutableLiveData()

    override fun initSplashScreenLiveData(): LiveData<SplashScreenStatus> = mutableMainState

    override fun initSplashScreen() = viewModelScope.launch {
        mutableMainState.value = SplashScreenStatus.INIT
        withContext(coroutineContext) {
            delay(SPLASH_DELAY_TIME)
        }
        mutableMainState.value = SplashScreenStatus.FINISH
    }

    enum class SplashScreenStatus {
        INIT,
        FINISH
    }

    companion object {
        private const val SPLASH_DELAY_TIME = 3000L
    }
}
