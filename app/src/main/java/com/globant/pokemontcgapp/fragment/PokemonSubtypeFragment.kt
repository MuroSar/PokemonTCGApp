package com.globant.pokemontcgapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.globant.domain.entity.PokemonSubtype
import com.globant.pokemontcgapp.adapter.PokemonSecondaryTypesAdapter
import com.globant.pokemontcgapp.databinding.FragmentPokemonAlltypesLayoutBinding
import com.globant.pokemontcgapp.util.getColumnsByOrientation
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel.Data
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonSubtypeFragment : Fragment() {

    private val pokemonSubtypeViewModel by viewModel<PokemonSubtypeViewModel>()
    private lateinit var binding: FragmentPokemonAlltypesLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPokemonAlltypesLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonSubtypeViewModel.getPokemonSubtypesLiveData().observe(::getLifecycle, ::updateUI)
    }

    override fun onResume() {
        super.onResume()
        binding.pokemonAlltypesLoading.visibility = View.GONE
        pokemonSubtypeViewModel.getPokemonSubtypes()
    }

    private fun updateUI(pokemonSubtypes: Data) {
        when (pokemonSubtypes.status) {
            Status.SUCCESS -> pokemonSubtypes.data?.pokemonSubtypesList?.let { showPokemonSubtypes(it) }
        }
    }

    private fun showPokemonSubtypes(pokemonSubtypes: List<PokemonSubtype>) {
        binding.pokemonAlltypesRecyclerView.apply {
            layoutManager =
                GridLayoutManager(
                    context,
                    resources.configuration.getColumnsByOrientation(
                        COLUMNS_PORTRAIT,
                        COLUMNS_LANDSCAPE
                    )
                )
            adapter = PokemonSecondaryTypesAdapter(pokemonSubtypes)
            visibility = View.VISIBLE
        }
    }

    companion object {
        private const val COLUMNS_PORTRAIT = 2
        private const val COLUMNS_LANDSCAPE = 4
    }
}
