package com.globant.pokemontcgapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.globant.domain.entity.PokemonCard
import com.globant.pokemontcgapp.databinding.ActivityPokemonCardDetailBinding
import com.globant.pokemontcgapp.util.Constant.POKEMON_CARD_ID
import com.globant.pokemontcgapp.util.Drawable
import com.globant.pokemontcgapp.util.Event
import com.globant.pokemontcgapp.util.StringResource
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel.Data
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonCardDetailActivity : AppCompatActivity() {

    private val pokemonCardDetailViewModel by viewModel<PokemonCardDetailViewModel>()
    private lateinit var binding: ActivityPokemonCardDetailBinding
    private lateinit var pokemonCardId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonCardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonCardId = intent.getStringExtra(POKEMON_CARD_ID)
        pokemonCardDetailViewModel.getPokemonCardLiveData().observe(::getLifecycle, ::updateUI)
    }

    override fun onResume() {
        super.onResume()
        binding.activityPokemonCardDetailLoading.visibility = View.GONE
        pokemonCardDetailViewModel.getPokemonCard(pokemonCardId)
    }

    private fun updateUI(data: Event<Data>) {
        val pokemonCardDetailData = data.getContentIfNotHandled()
        when (pokemonCardDetailData?.status) {
            Status.LOADING -> binding.activityPokemonCardDetailLoading.visibility = View.VISIBLE
            Status.SUCCESS -> pokemonCardDetailData.data?.let { showPokemonCardDetails(it) }
            Status.ERROR -> showPokemonCardDetailError(pokemonCardDetailData.error?.message)
        }
    }

    private fun showPokemonCardDetails(pokemonCard: PokemonCard) {
        binding.activityPokemonCardDetailLoading.visibility = View.GONE
        with(binding) {
            pokemonCard.let { card ->
                activityPokemonCardDetailName.text = getString(StringResource.activity_pokemon_card_detail_name_text, card.name)

                Glide.with(this@PokemonCardDetailActivity)
                    .load((card.image))
                    .placeholder(Drawable.pokemon_cardback)
                    .into(this.activityPokemonCardDetailImage)

                card.details?.nationalPokedexNumber?.let { pokedexNumber ->
                    activityPokemonCardDetailNationalPokedexNumber.apply {
                        text = getString(StringResource.activity_pokemon_card_detail_national_pokedex_number_text, pokedexNumber.toString())
                        visibility = View.VISIBLE
                    }
                }

                card.type?.let {
                    activityPokemonCardDetailType.apply {
                        text = getString(StringResource.activity_pokemon_card_detail_type_text, card.type)
                        visibility = View.VISIBLE
                    }
                }

                activityPokemonCardDetailSupertype.text =
                    getString(StringResource.activity_pokemon_card_detail_supertype_text, card.supertype)

                activityPokemonCardDetailSubtype.text = getString(StringResource.activity_pokemon_card_detail_subtype_text, card.subtype)

                card.details?.evolvesFrom?.let { evolvesFrom ->
                    activityPokemonCardDetailEvolvesFrom.apply {
                        text =
                            getString(StringResource.activity_pokemon_card_detail_evolves_from_text, evolvesFrom)
                        visibility = View.VISIBLE
                    }
                }

                card.details?.healthPoints?.let { healthPoints ->
                    if (healthPoints != NONE)
                        activityPokemonCardDetailHp.apply {
                            text =
                                getString(StringResource.activity_pokemon_card_detail_hp_text, healthPoints)
                            visibility = View.VISIBLE
                        }
                }

                activityPokemonCardDetailNumber.text =
                    getString(StringResource.activity_pokemon_card_detail_number_text, card.details?.number)

                if (card.details?.artist.isNullOrEmpty()) {
                    activityPokemonCardDetailArtist.text =
                        getString(StringResource.activity_pokemon_card_detail_artist_text, UNIDENTIFIED)
                } else {
                    activityPokemonCardDetailArtist.text =
                        getString(StringResource.activity_pokemon_card_detail_artist_text, card.details?.artist)
                }

                if (card.details?.rarity.isNullOrEmpty()) {
                    activityPokemonCardDetailRarity.text =
                        getString(StringResource.activity_pokemon_card_detail_rarity_text, UNIDENTIFIED)
                } else {
                    activityPokemonCardDetailRarity.text =
                        getString(StringResource.activity_pokemon_card_detail_rarity_text, card.details?.rarity)
                }

                activityPokemonCardDetailSeries.text =
                    getString(StringResource.activity_pokemon_card_detail_series_text, card.details?.series)

                activityPokemonCardDetailSet.text = getString(StringResource.activity_pokemon_card_detail_set_text, card.details?.set)

                activityPokemonCardDetailSetCode.text =
                    getString(StringResource.activity_pokemon_card_detail_set_code_text, card.details?.setCode)
            }
        }
    }

    private fun showPokemonCardDetailError(error: String?) {
        binding.activityPokemonCardDetailLoading.visibility = View.GONE
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val NONE = "None"
        private const val UNIDENTIFIED = "Unidentified"
        fun getIntent(context: Context, data: String): Intent =
            Intent(context, PokemonCardDetailActivity::class.java).apply {
                putExtra(POKEMON_CARD_ID, data)
            }
    }
}
