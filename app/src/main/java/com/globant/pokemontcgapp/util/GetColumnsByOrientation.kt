package com.globant.pokemontcgapp.util

import android.content.res.Configuration

fun Configuration.getColumnsByOrientation(portrait: Int, landscape: Int): Int =
    orientation.let {
        return when (it) {
            Configuration.ORIENTATION_PORTRAIT -> portrait
            Configuration.ORIENTATION_LANDSCAPE -> landscape
            else -> portrait
        }
    }
