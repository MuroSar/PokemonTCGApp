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
import com.globant.pokemontcgapp.adapter.PokemonSecondaryTypeSelected
import com.globant.pokemontcgapp.adapter.PokemonSecondaryTypesAdapter
import com.globant.pokemontcgapp.databinding.FragmentPokemonAlltypesLayoutBinding
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.util.getColumnsByOrientation
import com.globant.pokemontcgapp.util.pokemonSubtypesResources
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonSubtypeViewModel.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonSubtypeFragment : Fragment(), PokemonSecondaryTypeSelected {

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

    private fun updateUI(data: Event<PokemonSubtypeViewModel.Data>) {
        val pokemonSubtypesData = data.getContentIfNotHandled()
        when (pokemonSubtypesData?.status) {
            Status.LOADING -> binding.pokemonAlltypesLoading.visibility = View.VISIBLE
            Status.SUCCESS -> showPokemonSubtypes(pokemonSubtypesData.data)
            Status.ERROR -> showPokemonSubtypesError(pokemonSubtypesData.error?.message)
            Status.ON_SUBTYPE_CLICKED -> pokemonSubtypesData.pokemonSubtype?.let { onSubtypeClicked(it) }
        }
    }

    private fun showPokemonSubtypes(pokemonSubtypes: List<SecondaryTypes>) {
        binding.pokemonAlltypesLoading.visibility = View.GONE
        pokemonSubtypes.let {
            val pokemonSubtypesAdapter = PokemonSecondaryTypesAdapter(pokemonSubtypes, this)
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
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun onPokemonSecondaryTypeSelected(secondaryTypeSelected: SecondaryTypes) {
        pokemonSubtypeViewModel.onPokemonSubtypeSelected(secondaryTypeSelected)
    }

    private fun onSubtypeClicked(subtypeSelected: SecondaryTypes) {
        startActivity(
            PokemonCardListActivity.getIntent(
                requireContext(),
                Triple(SUBTYPE, subtypeSelected.name, subtypeSelected.bgColor)
            )
        )
    }

    companion object {
        private const val SUBTYPE = "subtype"
        private const val COLUMNS_PORTRAIT = 2
        private const val COLUMNS_LANDSCAPE = 4
    }
}
