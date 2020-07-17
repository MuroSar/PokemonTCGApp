package com.globant.pokemontcgapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.globant.domain.entity.PokemonSupertype
import com.globant.pokemontcgapp.adapter.PokemonSupertypesAdapter
import com.globant.pokemontcgapp.databinding.FragmentPokemonSupertypeLayoutBinding
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.util.getColumnsByOrientation
import com.globant.pokemontcgapp.util.pokemonSupertypesResources
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel.Data
import com.globant.pokemontcgapp.viewmodel.PokemonSupertypeViewModel.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonSupertypeFragment : Fragment() {

    private val pokemonSupertypeViewModel by viewModel<PokemonSupertypeViewModel>()
    private lateinit var binding: FragmentPokemonSupertypeLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPokemonSupertypeLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonSupertypeViewModel.getPokemonSupertypesLiveData().observe(::getLifecycle, ::updateUI)
    }

    override fun onResume() {
        super.onResume()
        binding.pokemonSupertypeLoading.visibility = View.GONE
        pokemonSupertypeViewModel.getPokemonSupertypes(pokemonSupertypesResources)
    }

    private fun updateUI(data: Event<Data<List<PokemonSupertype>>>) {
        val pokemonSupertypesData = data.getContentIfNotHandled()
        when (pokemonSupertypesData?.status) {
            Status.LOADING -> binding.pokemonSupertypeLoading.visibility = View.VISIBLE
            Status.SUCCESS -> pokemonSupertypesData.data?.let { showPokemonSupertypes(it) }
            Status.ERROR -> showPokemonSupertypesError(pokemonSupertypesData.error?.message)
        }
    }

    private fun showPokemonSupertypes(pokemonSupertypes: List<PokemonSupertype>) {
        binding.pokemonSupertypeLoading.visibility = View.GONE
        binding.pokemonSupertypeRecyclerView.apply {
            layoutManager =
                GridLayoutManager(
                    context,
                    resources.configuration.getColumnsByOrientation(
                        COLUMNS_PORTRAIT,
                        COLUMNS_LANDSCAPE
                    )
                )
            adapter = PokemonSupertypesAdapter(pokemonSupertypes)
            visibility = View.VISIBLE
        }
    }

    private fun showPokemonSupertypesError(error: String?) {
        binding.pokemonSupertypeLoading.visibility = View.GONE
        error?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
    }

    companion object {
        private const val COLUMNS_PORTRAIT = 1
        private const val COLUMNS_LANDSCAPE = 3
    }
}
