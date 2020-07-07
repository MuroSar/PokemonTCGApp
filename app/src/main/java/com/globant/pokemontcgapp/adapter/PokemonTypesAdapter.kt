package com.globant.pokemontcgapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.globant.domain.entity.PokemonType
import com.globant.pokemontcgapp.R
import com.globant.pokemontcgapp.databinding.PokemonTypeElementBinding
import com.globant.pokemontcgapp.util.pokemonTypeImageMapper

class PokemonTypesAdapter(private val pokemonTypes: List<PokemonType>) : RecyclerView.Adapter<PokemonTypesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pokemon_type_element,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonTypes[position])
    }

    override fun getItemCount(): Int = pokemonTypes.size

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonTypeElementBinding.bind(itemView)

        fun bind(item: PokemonType) = with(itemView) {
            binding.pokemonTypeTextViewName.text = item.name
            Glide.with(context)
                .load(pokemonTypeImageMapper(item))
                .transform(CircleCrop())
                .into(binding.pokemonTypeImageViewSymbol)
        }
    }
}
