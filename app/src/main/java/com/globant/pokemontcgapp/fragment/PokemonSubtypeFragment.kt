package com.globant.pokemontcgapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.globant.domain.entity.SecondaryTypes
import com.globant.pokemontcgapp.activity.PokemonCardListActivity
import com.globant.pokemontcgapp.adapter.PokemonSecondaryTypesAdapter
import com.globant.pokemontcgapp.databinding.FragmentPokemonAlltypesLayoutBinding
import com.globant.pokemontcgapp.util.Constant.POKEMON_GROUP
import com.globant.pokemontcgapp.util.Constant.SELECTION
import com.globant.pokemontcgapp.util.Constant.SELECTION_COLOR
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.util.getColumnsByOrientation
import com.globant.pokemontcgapp.util.pokemonSubtypesResources
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
        pokemonSubtypeViewModel.getPokemonSubtypes(pokemonSubtypesResources)
    }

    private fun updateUI(data: Event<Data<List<SecondaryTypes>>>) {
        val pokemonSupertypesData = data.getContentIfNotHandled()
        when (pokemonSupertypesData?.status) {
            Status.LOADING -> binding.pokemonAlltypesLoading.visibility = View.VISIBLE
            Status.SUCCESS -> pokemonSupertypesData.data?.let { showPokemonSubtypes(it) }
            Status.ERROR -> showPokemonSubtypesError(pokemonSupertypesData.error?.message)
        }
    }

    private fun showPokemonSubtypes(pokemonSubtypes: List<SecondaryTypes>) {
        binding.pokemonAlltypesLoading.visibility = View.GONE
        pokemonSubtypes.let {
            val pokemonSubtypesAdapter = PokemonSecondaryTypesAdapter(pokemonSubtypes) { pokemonSubtypeElement ->
                startActivity(
                    context?.let { it1 ->
                        PokemonCardListActivity.getIntent(it1).apply {
                            putExtra(POKEMON_GROUP, SUBTYPE)
                            putExtra(SELECTION, pokemonSubtypeElement.name)
                            putExtra(SELECTION_COLOR, pokemonSubtypeElement.bgColor)
                        }
                    }
                )
            }
            binding.pokemonAlltypesRecyclerView.apply {
                layoutManager =
                    GridLayoutManager(
                        context,
                        resources.configuration.getColumnsByOrientation(
                            COLUMNS_PORTRAIT,
                            COLUMNS_LANDSCAPE
                        )
                    )
                adapter = pokemonSubtypesAdapter
                visibility = View.VISIBLE
            }
        }
    }

    private fun showPokemonSubtypesError(error: String?) {
        binding.pokemonAlltypesLoading.visibility = View.GONE
        error?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
    }

    companion object {
        private const val SUBTYPE = "subtype"
        private const val COLUMNS_PORTRAIT = 2
        private const val COLUMNS_LANDSCAPE = 4
    }
}
