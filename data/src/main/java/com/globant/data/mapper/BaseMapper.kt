package com.globant.data.mapper

interface BaseMapper<E, D, R> {
    fun transform(type: E, resources: R? = null): D
    // This BaseMapper needed a third generic value to handle the different hash maps that
    // deliver the resources for the entities, and it's nullable and initialized null to avoid
    // errors on parameters insertions
}
