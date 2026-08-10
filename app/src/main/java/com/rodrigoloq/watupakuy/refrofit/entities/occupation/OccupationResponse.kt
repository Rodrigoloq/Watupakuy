package com.rodrigoloq.watupakuy.refrofit.entities.occupation

data class OccupationResponse(val departamento: String = "",
    val fecha: String = "",
    val ocupacionEstimadaconsolidado: Double = 0.0,
    val ocupacionEstimadahotel: Double = 0.0,
    val ocupacionEstimadahostal: Double = 0.0,
    val ocupacionEstimadaotros: Double = 0.0
    )
