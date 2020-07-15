package com.globant.data.mapper

import com.globant.data.database.entity.PokemonTypeRoom
import com.globant.domain.entity.PokemonType

class PokemonTypeRoomMapper : BaseMapper<List<PokemonTypeRoom>, MutableList<PokemonType>, MutableMap<String, Pair<Int, Int>>?> {
    override fun transform(type: List<PokemonTypeRoom>, resources: MutableMap<String, Pair<Int, Int>>?): MutableList<PokemonType> {
        val pokemonTypeRoomReturnList: MutableList<PokemonType> = mutableListOf()

        type.map { typeRoom ->
            pokemonTypeRoomReturnList.add(PokemonType(typeRoom.name, typeRoom.image, typeRoom.bgColor))
        }
        return pokemonTypeRoomReturnList
    }

    fun transformToPokemonTypeRoom(pokemonType: PokemonType): PokemonTypeRoom =
        PokemonTypeRoom(
            pokemonType.name,
            pokemonType.image,
            pokemonType.bgColor
        )
}
