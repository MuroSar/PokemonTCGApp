package com.globant.pokemontcgapp.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.globant.pokemontcgapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_layout)

        activityScope.launch {
            val imageView: ImageView = findViewById(R.id.activity_splash_screen_title_image_view)
            Glide.with(applicationContext).load(R.drawable.app_logo).into(imageView)

            delay(3000)
            startActivity(MainActivity.getIntent(this@SplashScreenActivity))
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}
