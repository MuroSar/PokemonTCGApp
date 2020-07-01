package com.globant.pokemontcgapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.globant.pokemontcgapp.R
import com.globant.pokemontcgapp.adapter.ViewPagerFragmentAdapter
import com.globant.pokemontcgapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainPokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPagerWithFragments()
    }

    private fun initViewPagerWithFragments() {
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = ViewPagerFragmentAdapter(supportFragmentManager, lifecycle)

        Glide.with(applicationContext).load(R.drawable.app_logo).into(binding.activityMainAppTitleIcon)

        val listOfFragmentNames: List<String> =
            listOf(getString(R.string.pokemon_types), getString(R.string.pokemon_supertypes), getString(R.string.pokemon_subtypes))
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position -> tab.text = listOfFragmentNames[position] }.attach()
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, MainPokemonActivity::class.java)
    }
}
