package com.rodrigoloq.watupakuy.data.refrofit.entities.information

import com.google.gson.annotations.SerializedName

data class Region(val title: String,
    val description: String,
    @SerializedName("best_places") val bestPlaces: List<BestPlace> = listOf())
