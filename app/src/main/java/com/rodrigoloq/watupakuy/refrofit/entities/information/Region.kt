package com.rodrigoloq.watupakuy.refrofit.entities.information

data class Region(val title: String = "",
    val description: String = "",
    val best_places: List<BestPlace> = listOf())
