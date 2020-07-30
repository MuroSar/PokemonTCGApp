package com.globant.pokemontcgapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.globant.domain.entity.PokemonCard
import com.globant.domain.util.NONE
import com.globant.domain.util.ZERO_INT
import com.globant.pokemontcgapp.databinding.ActivityPokemonCardDetailBinding
import com.globant.pokemontcgapp.util.Constant.POKEMON_CARD_ID
import com.globant.pokemontcgapp.util.Constant.POKEMON_CARD_IMAGE
import com.globant.pokemontcgapp.util.Constant.POKEMON_CARD_NAME
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
    private lateinit var pokemonCardName: String
    private lateinit var pokemonCardImage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonCardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonCardId = intent.getStringExtra(POKEMON_CARD_ID)
        pokemonCardName = intent.getStringExtra(POKEMON_CARD_NAME)
        pokemonCardImage = intent.getStringExtra(POKEMON_CARD_IMAGE)

        initUi(pokemonCardName, pokemonCardImage)

        pokemonCardDetailViewModel.getPokemonCardLiveData().observe(::getLifecycle, ::updateUI)
    }

    override fun onResume() {
        super.onResume()
        binding.activityPokemonCardDetailLoading.visibility = View.GONE
        pokemonCardDetailViewModel.getPokemonCard(pokemonCardId)
    }

    private fun initUi(pokemonCardName: String, pokemonCardImage: String) {
        supportPostponeEnterTransition()

        binding.activityPokemonCardDetailImage.transitionName = pokemonCardName

        Glide.with(this@PokemonCardDetailActivity)
            .load((pokemonCardImage))
            .placeholder(Drawable.pokemon_cardback)
            .into(binding.activityPokemonCardDetailImage)

        supportStartPostponedEnterTransition()
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
                activityPokemonCardDetailName.text = getString(StringResource.activity_pokemon_card_detail_name_text, pokemonCardName)

                if (card.details.nationalPokedexNumber != ZERO_INT)
                    activityPokemonCardDetailNationalPokedexNumber.apply {
                        text = getString(
                            StringResource.activity_pokemon_card_detail_national_pokedex_number_text,
                            card.details.nationalPokedexNumber.toString()
                        )
                        visibility = View.VISIBLE
                    }

                if (card.type.isNotEmpty())
                    activityPokemonCardDetailType.apply {
                        text = getString(StringResource.activity_pokemon_card_detail_type_text, card.type)
                        visibility = View.VISIBLE
                    }

                activityPokemonCardDetailSupertype.text =
                    getString(StringResource.activity_pokemon_card_detail_supertype_text, card.supertype)

                activityPokemonCardDetailSubtype.text = getString(StringResource.activity_pokemon_card_detail_subtype_text, card.subtype)

                if (card.details.evolvesFrom.isNotEmpty())
                    activityPokemonCardDetailEvolvesFrom.apply {
                        text = getString(StringResource.activity_pokemon_card_detail_evolves_from_text, card.details.evolvesFrom)
                        visibility = View.VISIBLE
                    }

                if (card.details.healthPoints != NONE)
                    activityPokemonCardDetailHp.apply {
                        text = getString(StringResource.activity_pokemon_card_detail_hp_text, card.details.healthPoints)
                        visibility = View.VISIBLE
                    }

                activityPokemonCardDetailNumber.text =
                    getString(StringResource.activity_pokemon_card_detail_number_text, card.details.number)

                if (card.details.artist.isEmpty()) {
                    activityPokemonCardDetailArtist.text =
                        getString(StringResource.activity_pokemon_card_detail_artist_text, UNIDENTIFIED)
                } else {
                    activityPokemonCardDetailArtist.text =
                        getString(StringResource.activity_pokemon_card_detail_artist_text, card.details.artist)
                }

                if (card.details.rarity.isEmpty()) {
                    activityPokemonCardDetailRarity.text =
                        getString(StringResource.activity_pokemon_card_detail_rarity_text, UNIDENTIFIED)
                } else {
                    activityPokemonCardDetailRarity.text =
                        getString(StringResource.activity_pokemon_card_detail_rarity_text, card.details.rarity)
                }

                activityPokemonCardDetailSeries.text =
                    getString(StringResource.activity_pokemon_card_detail_series_text, card.details.series)

                activityPokemonCardDetailSet.text = getString(StringResource.activity_pokemon_card_detail_set_text, card.details.set)

                activityPokemonCardDetailSetCode.text =
                    getString(StringResource.activity_pokemon_card_detail_set_code_text, card.details.setCode)
            }
        }
    }

    private fun showPokemonCardDetailError(error: String?) {
        binding.activityPokemonCardDetailLoading.visibility = View.GONE
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val UNIDENTIFIED = "Unidentified"
        fun getIntent(context: Context, data: Triple<String, String, String>): Intent =
            Intent(context, PokemonCardDetailActivity::class.java).apply {
                putExtra(POKEMON_CARD_ID, data.first)
                putExtra(POKEMON_CARD_NAME, data.second)
                putExtra(POKEMON_CARD_IMAGE, data.third)
            }
    }
}
