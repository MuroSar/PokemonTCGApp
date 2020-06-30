package com.globant.pokemontcgapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.pokemontcgapp.contract.SplashScreenContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenViewModel : ViewModel(), SplashScreenContract.ViewModel {

    private var mutableMainState: MutableLiveData<SplashScreenData> = MutableLiveData()

    override fun initSplashScreenLiveData(): LiveData<SplashScreenData> = mutableMainState

    override fun initSplashScreen() = viewModelScope.launch {
        mutableMainState.value = SplashScreenData(SplashScreenStatus.INIT)
        withContext(Dispatchers.IO) {
            delay(SPLASH_DELAY_TIME)
        }
        mutableMainState.value = SplashScreenData(SplashScreenStatus.FINISH)
    }

    data class SplashScreenData(val status: SplashScreenStatus)

    enum class SplashScreenStatus {
        INIT,
        FINISH
    }

    companion object {
        private const val SPLASH_DELAY_TIME = 3000L
    }
}
