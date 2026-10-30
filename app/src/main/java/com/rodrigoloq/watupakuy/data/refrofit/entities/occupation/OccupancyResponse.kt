package com.rodrigoloq.watupakuy.data.refrofit.entities.occupation

import com.google.gson.annotations.SerializedName

data class OccupancyResponse(
    val departamento: String,
    val fecha: String,
    val clase: String,
    @SerializedName("prediccion_tnoh") val totalPercent: Double,
    @SerializedName("ajustado_a_rango") val clampedToRange: Boolean,
    @SerializedName("ultimo_dato_observado") val lastObservedDate: String,
    @SerializedName("por_clase") val byClass: List<OccupancyByClass> = listOf()
)
