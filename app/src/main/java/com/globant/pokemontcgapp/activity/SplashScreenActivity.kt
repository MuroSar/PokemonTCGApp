package com.globant.pokemontcgapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.globant.pokemontcgapp.R
import com.globant.pokemontcgapp.databinding.ActivitySplashScreenLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityScope.launch {

            Glide.with(applicationContext).load(R.drawable.app_logo).into(binding.activitySplashScreenTitleImageView)

            delay(SPLASH_DELAY_TIME)
            startActivity(MainActivity.getIntent(this@SplashScreenActivity))
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

    companion object {
        private const val SPLASH_DELAY_TIME = 3000L
    }
}
