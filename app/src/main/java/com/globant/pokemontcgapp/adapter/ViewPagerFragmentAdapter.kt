package com.globant.pokemontcgapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.globant.pokemontcgapp.fragment.PokemonSubtypeFragment
import com.globant.pokemontcgapp.fragment.PokemonSupertypeFragment
import com.globant.pokemontcgapp.fragment.PokemonTypeFragment

class ViewPagerFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragments: List<Fragment> = listOf(PokemonTypeFragment(), PokemonSupertypeFragment(), PokemonSubtypeFragment())

    override fun createFragment(position: Int): Fragment = fragments[position]

    override fun getItemCount(): Int = fragments.size
}
