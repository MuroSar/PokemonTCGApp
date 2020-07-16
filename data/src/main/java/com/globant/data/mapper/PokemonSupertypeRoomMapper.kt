package com.globant.data.mapper

import com.globant.data.database.entity.PokemonSupertypeRoom
import com.globant.domain.entity.PokemonSupertype

class PokemonSupertypeRoomMapper : BaseMapper<List<PokemonSupertypeRoom>, MutableList<PokemonSupertype>, MutableMap<String, Int>?> {
    override fun transform(type: List<PokemonSupertypeRoom>, resources: MutableMap<String, Int>?): MutableList<PokemonSupertype> {
        val pokemonSupertypeRoomReturnList: MutableList<PokemonSupertype> = mutableListOf()

        type.map { supertypeRoom ->
            pokemonSupertypeRoomReturnList.add(PokemonSupertype(supertypeRoom.name, supertypeRoom.bgColor))
        }
        return pokemonSupertypeRoomReturnList
    }

    fun transformToPokemonSupertypeRoom(pokemonSupertype: PokemonSupertype): PokemonSupertypeRoom =
        PokemonSupertypeRoom(
            pokemonSupertype.name,
            pokemonSupertype.bgColor
        )
}
