package com.globant.pokemontcgapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.globant.pokemontcgapp.R
import com.globant.pokemontcgapp.databinding.ActivitySplashScreenLayoutBinding
import com.globant.pokemontcgapp.viewmodel.SplashScreenViewModel
import com.globant.pokemontcgapp.viewmodel.SplashScreenViewModel.SplashScreenStatus.FINISH
import com.globant.pokemontcgapp.viewmodel.SplashScreenViewModel.SplashScreenStatus.INIT

import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashScreenViewModel>()
    private lateinit var binding: ActivitySplashScreenLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initSplashScreenLiveData().observe(::getLifecycle, ::updateUI)
        viewModel.initSplashScreen()
    }

    private fun updateUI(status: SplashScreenViewModel.SplashScreenStatus) {
        when (status) {
            INIT -> showSplashScreen()
            FINISH -> changeActivity()
        }
    }

    private fun showSplashScreen() {
        Glide.with(applicationContext).load(R.drawable.app_logo).into(binding.activitySplashScreenTitleImageView)
    }

    private fun changeActivity() {
        startActivity(MainActivity.getIntent(this@SplashScreenActivity))
        finish()
    }
}
