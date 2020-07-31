package com.globant.pokemontcgapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.NONE
import com.globant.pokemontcgapp.databinding.ActivityPokemonCardDetailBinding
import com.globant.pokemontcgapp.util.Constant.ID
import com.globant.pokemontcgapp.util.Drawable
import com.globant.pokemontcgapp.util.StringResource
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel.Data
import com.globant.pokemontcgapp.viewmodel.PokemonCardDetailViewModel.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonCardDetailActivity : AppCompatActivity() {

    private val pokemonCardDetailViewModel by viewModel<PokemonCardDetailViewModel>()
    private lateinit var binding: ActivityPokemonCardDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonCardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonCardDetailViewModel.getPokemonCardLiveData().observe(::getLifecycle, ::updateUI)
    }

    override fun onResume() {
        super.onResume()
        binding.activityPokemonCardDetailLoader.visibility = View.GONE
        pokemonCardDetailViewModel.getPokemonCard()
    }

    private fun updateUI(pokemonCardDetailData: Data) {
        when (pokemonCardDetailData.status) {
            Status.LOADING -> binding.activityPokemonCardDetailLoader.visibility = View.VISIBLE
            Status.SUCCESS -> showPokemonCardDetails(pokemonCardDetailData.data)
        }
    }

    private fun showPokemonCardDetails(pokemonCard: PokemonCard) {
        with(binding) {
            pokemonCard.let { card ->
                activityPokemonCardDetailName.text = getString(StringResource.activity_pokemon_card_detail_name_text, card.name)

                Glide.with(this@PokemonCardDetailActivity)
                    .load((card.image))
                    .placeholder(Drawable.pokemon_cardback)
                    .into(this.activityPokemonCardDetailImage)

                card.details?.nationalPokedexNumber?.let { pokedexNumber ->
                    activityPokemonCardDetailNationalPokedexNumber.apply {
                        text = getString(StringResource.activity_pokemon_card_detail_national_pokedex_number_text, pokedexNumber)
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

                if (card.details?.healthPoints != NONE)
                    activityPokemonCardDetailHp.apply {
                        text =
                            getString(StringResource.activity_pokemon_card_detail_hp_text, card.details?.healthPoints)
                        visibility = View.VISIBLE
                    }

                activityPokemonCardDetailNumber.text =
                    getString(StringResource.activity_pokemon_card_detail_number_text, card.details?.number)

                activityPokemonCardDetailArtist.text =
                    getString(StringResource.activity_pokemon_card_detail_artist_text, card.details?.artist)

                activityPokemonCardDetailRarity.text =
                    getString(StringResource.activity_pokemon_card_detail_rarity_text, card.details?.rarity)

                activityPokemonCardDetailSeries.text =
                    getString(StringResource.activity_pokemon_card_detail_series_text, card.details?.series)

                activityPokemonCardDetailSet.text = getString(StringResource.activity_pokemon_card_detail_set_text, card.details?.set)

                activityPokemonCardDetailSetCode.text =
                    getString(StringResource.activity_pokemon_card_detail_set_code_text, card.details?.setCode)
            }
        }
    }

    companion object {
        fun getIntent(context: Context, data: String): Intent =
            Intent(context, PokemonCardDetailActivity::class.java).apply {
                putExtra(ID, data)
            }
    }
}
