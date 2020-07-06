package com.globant.pokemontcgapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.globant.pokemontcgapp.adapter.PokemonTypesAdapter
import com.globant.pokemontcgapp.databinding.FragmentPokemonTypeLayoutBinding
import com.globant.pokemontcgapp.util.PokemonTypeData
import com.globant.pokemontcgapp.util.PokemonTypeState
import com.globant.pokemontcgapp.util.getColumnsByOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel

class PokemonTypeFragment : Fragment() {

    private val pokemonTypeViewModel by viewModel<PokemonTypeViewModel>()
    private val pokemonTypesAdapter = PokemonTypesAdapter()
    private lateinit var binding: FragmentPokemonTypeLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPokemonTypeLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonTypeViewModel.getPokemonTypesLiveData().observe(::getLifecycle, ::updateUI)
        pokemonTypeViewModel.getPokemonTypes()
    }

    private fun updateUI(pokemonTypes: PokemonTypeData) {
        when (pokemonTypes.state) {
            PokemonTypeState.LOADING -> binding.pokemonTypeProgressBar.visibility = View.VISIBLE
            PokemonTypeState.POKEMON_TYPE_DATA -> pokemonTypes.data?.pokemonTypesList?.let { showPokemonTypes(it) }
        }
    }

    private fun showPokemonTypes(pokemonTypes: List<String>) {
        pokemonTypesAdapter.submitList(pokemonTypes)

        binding.pokemonTypeProgressBar.visibility = View.GONE
        binding.pokemonTypeRecyclerView.layoutManager =
            GridLayoutManager(context, resources.configuration.getColumnsByOrientation(COLUMNS_PORTRAIT, COLUMNS_LANDSCAPE))
        binding.pokemonTypeRecyclerView.adapter = pokemonTypesAdapter
    }

    companion object {
        private const val COLUMNS_PORTRAIT = 4
        private const val COLUMNS_LANDSCAPE = 6
    }
}
