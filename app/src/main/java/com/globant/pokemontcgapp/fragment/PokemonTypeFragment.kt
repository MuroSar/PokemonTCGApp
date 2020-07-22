package com.globant.pokemontcgapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.globant.domain.entity.PokemonType
import com.globant.pokemontcgapp.activity.PokemonCardListActivity
import com.globant.pokemontcgapp.adapter.PokemonTypesAdapter
import com.globant.pokemontcgapp.databinding.FragmentPokemonAlltypesLayoutBinding
import com.globant.pokemontcgapp.util.Constant.POKEMON_GROUP
import com.globant.pokemontcgapp.util.Constant.SELECTION
import com.globant.pokemontcgapp.util.Constant.SELECTION_COLOR
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.util.getColumnsByOrientation
import com.globant.pokemontcgapp.util.pokemonTypesResources
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel.Data
import com.globant.pokemontcgapp.viewmodel.PokemonTypeViewModel.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonTypeFragment : Fragment() {

    private val pokemonTypeViewModel by viewModel<PokemonTypeViewModel>()
    private lateinit var binding: FragmentPokemonAlltypesLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPokemonAlltypesLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonTypeViewModel.getPokemonTypesLiveData().observe(::getLifecycle, ::updateUI)
    }

    override fun onResume() {
        super.onResume()
        binding.pokemonAlltypesLoading.visibility = View.GONE
        pokemonTypeViewModel.getPokemonTypes(pokemonTypesResources)
    }

    private fun updateUI(data: Event<Data<List<PokemonType>>>) {
        val pokemonTypesData = data.getContentIfNotHandled()
        when (pokemonTypesData?.status) {
            Status.LOADING -> binding.pokemonAlltypesLoading.visibility = View.VISIBLE
            Status.SUCCESS -> pokemonTypesData.data?.let { showPokemonTypes(it) }
            Status.ERROR -> showPokemonTypesError(pokemonTypesData.error?.message)
        }
    }

    private fun showPokemonTypes(pokemonTypes: List<PokemonType>) {
        binding.pokemonAlltypesLoading.visibility = View.GONE
        pokemonTypes.let {
            val pokemonTypesAdapter = PokemonTypesAdapter(pokemonTypes) { pokemonTypeElement ->
                startActivity(
                    context?.let { it1 ->
                        PokemonCardListActivity.getIntent(it1).apply {
                            putExtra(POKEMON_GROUP, TYPE)
                            putExtra(SELECTION, pokemonTypeElement.name)
                            putExtra(SELECTION_COLOR, pokemonTypeElement.bgColor)
                        }
                    }
                )
            }
            binding.pokemonAlltypesRecyclerView.apply {
                layoutManager =
                    GridLayoutManager(context, resources.configuration.getColumnsByOrientation(COLUMNS_PORTRAIT, COLUMNS_LANDSCAPE))
                adapter = pokemonTypesAdapter
                visibility = View.VISIBLE
            }
        }
    }

    private fun showPokemonTypesError(error: String?) {
        binding.pokemonAlltypesLoading.visibility = View.GONE
        error?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
    }

    companion object {
        private const val TYPE = "type"
        private const val COLUMNS_PORTRAIT = 4
        private const val COLUMNS_LANDSCAPE = 6
    }
}
