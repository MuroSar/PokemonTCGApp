package com.globant.pokemontcgapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.globant.domain.entity.PokemonSupertype
import com.globant.pokemontcgapp.R
import com.globant.pokemontcgapp.databinding.PokemonSupertypeElementBinding

class PokemonSupertypesAdapter(private val pokemonSupertypes: List<PokemonSupertype>) :
    RecyclerView.Adapter<PokemonSupertypesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pokemon_supertype_element,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonSupertypes[position])
    }

    override fun getItemCount(): Int = pokemonSupertypes.size

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonSupertypeElementBinding.bind(itemView)

        fun bind(item: PokemonSupertype) = with(itemView) {
            binding.pokemonSupertypeTextViewName.text = item.name
            binding.pokemonSupertypeCardView.setCardBackgroundColor(ContextCompat.getColor(context, item.bgColor))
        }
    }
}
