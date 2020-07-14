package com.globant.data.mapper

interface BaseMapper<E, D> {
    fun transform(type: E, resourcesMap: MutableMap<String, Pair<Int, Int>>? = null): D
}
