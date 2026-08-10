package com.rodrigoloq.watupakuy.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "occupation_predictions")
data class OccupationPredictions(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val departamento: String,
    val fecha: String,
    val ocupacionEstimadaconsolidado: Double,
    val ocupacionEstimadahotel: Double,
    val ocupacionEstimadahostal: Double,
    val ocupacionEstimadaotros: Double
)
