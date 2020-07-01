package com.globant.pokemontcgapp.viewmodel.contract

import androidx.lifecycle.LiveData
import com.globant.pokemontcgapp.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.Job

interface SplashScreenContract {
    interface ViewModel {
        fun initSplashScreen(): Job
        fun initSplashScreenLiveData(): LiveData<SplashScreenViewModel.SplashScreenStatus>
    }
}
