package com.globant.pokemontcgapp.util

data class Data<RequestData>(var status: Status, var data: RequestData? = null, var error: Exception? = null)

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
