package com.globant.pokemontcgapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.globant.domain.entity.PokemonCard
import com.globant.pokemontcgapp.adapter.PokemonCardListAdapter
import com.globant.pokemontcgapp.databinding.ActivityPokemonCardListBinding
import com.globant.pokemontcgapp.util.Constant.POKEMON_GROUP
import com.globant.pokemontcgapp.util.Constant.SELECTION
import com.globant.pokemontcgapp.util.Constant.SELECTION_COLOR
import com.globant.pokemontcgapp.util.getColumnsByOrientation
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel.Data
import com.globant.pokemontcgapp.viewmodel.PokemonCardListViewModel.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonCardListActivity : AppCompatActivity() {

    private val pokemonCardListViewModel by viewModel<PokemonCardListViewModel>()
    private lateinit var binding: ActivityPokemonCardListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonCardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selection = intent.getStringExtra(SELECTION)
        val selectionColor = intent.getIntExtra(SELECTION_COLOR, DEFAULT_VALUE)
        initUi(selection, selectionColor)
        pokemonCardListViewModel.getPokemonCardListLiveData().observe(::getLifecycle, ::updateUI)
    }

    override fun onResume() {
        super.onResume()
        binding.activityPokemonCardListLoading.visibility = View.GONE
        pokemonCardListViewModel.getPokemonCardList()
    }

    private fun initUi(selection: String?, selectionColor: Int) {
        binding.activityPokemonCardListAppBarTitle.text = selection
        binding.activityPokemonCardListAppbarLayout.setBackgroundColor(ContextCompat.getColor(this, selectionColor))
    }

    private fun updateUI(pokemonCardList: Data) {
        when (pokemonCardList.status) {
            Status.SUCCESS -> pokemonCardList.data?.pokemonCardList?.let { showPokemonCardList(it) }
        }
    }

    private fun showPokemonCardList(pokemonCardList: List<PokemonCard>) {
        binding.activityPokemonCardListRecyclerView.apply {
            layoutManager =
                GridLayoutManager(
                    context,
                    resources.configuration.getColumnsByOrientation(
                        COLUMNS_PORTRAIT,
                        COLUMNS_LANDSCAPE
                    )
                )
            adapter = PokemonCardListAdapter(pokemonCardList)
            visibility = View.VISIBLE
        }
    }

    companion object {
        private const val DEFAULT_VALUE = -1
        private const val COLUMNS_PORTRAIT = 1
        private const val COLUMNS_LANDSCAPE = 2
        fun getIntent(context: Context, data: Triple<String, String, Int>): Intent =
            Intent(context, PokemonCardListActivity::class.java).apply {
                putExtra(POKEMON_GROUP, data.first)
                putExtra(SELECTION, data.second)
                putExtra(SELECTION_COLOR, data.third)
            }
    }
}
