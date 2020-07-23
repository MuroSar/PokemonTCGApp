package com.globant.pokemontcgapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.globant.domain.entity.PokemonType
import com.globant.pokemontcgapp.R
import com.globant.pokemontcgapp.databinding.PokemonTypeElementBinding

interface PokemonTypeSelected {
    fun onPokemonTypeSelected(typeSelected: PokemonType)
}

class PokemonTypesAdapter(private val pokemonTypes: List<PokemonType>, private val onPokemonTypeClicked: PokemonTypeSelected) :
    RecyclerView.Adapter<PokemonTypesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pokemon_type_element,
                parent,
                false
            ),
            onPokemonTypeClicked
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonTypes[position])
    }

    override fun getItemCount(): Int = pokemonTypes.size

    class ViewHolder(itemView: View, private val onPokemonTypeClicked: PokemonTypeSelected) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonTypeElementBinding.bind(itemView)

        fun bind(item: PokemonType) = with(itemView) {
            setOnClickListener { onPokemonTypeClicked.onPokemonTypeSelected(item) }
            binding.pokemonTypeTextViewName.text = item.name
            binding.pokemonTypeCardView.setCardBackgroundColor(ContextCompat.getColor(context, item.bgColor))
            Glide.with(context)
                .load((item.image))
                .transform(CircleCrop())
                .into(binding.pokemonTypeImageViewSymbol)
        }
    }
}
