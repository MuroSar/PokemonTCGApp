package com.globant.pokemontcgapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.globant.domain.entity.PokemonCard
import com.globant.pokemontcgapp.R
import com.globant.pokemontcgapp.databinding.PokemonCardListElementBinding
import com.globant.pokemontcgapp.util.Drawable

class PokemonCardListAdapter(private val pokemonCardList: List<PokemonCard>) :
    RecyclerView.Adapter<PokemonCardListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pokemon_card_list_element,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonCardList[position])
    }

    override fun getItemCount(): Int = pokemonCardList.size

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonCardListElementBinding.bind(itemView)

        fun bind(item: PokemonCard) = with(itemView) {
            binding.pokemonCardListViewName.text = item.name
            Glide.with(context)
                .load((item.image))
                .placeholder(Drawable.pokemon_cardback)
                .into(binding.pokemonCardListImageViewImage)
        }
    }
}
