package com.rodrigoloq.watupakuy.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigoloq.watupakuy.refrofit.entities.occupation.OccupationResponse
import com.rodrigoloq.watupakuy.room.entities.OccupationPredictions

@Dao
interface OccupationPredictionsDAO {

    @Insert
    suspend fun addPrediction(prediction: OccupationPredictions): Long

    @Query("SELECT EXISTS(SELECT 1 FROM occupation_predictions WHERE departamento = :departamento AND fecha = :fecha)")
    suspend fun doesPredictionExists(departamento: String, fecha: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM occupation_predictions WHERE departamento = :departamento)")
    suspend fun doesRegionExists(departamento: String): Boolean

    @Query("SELECT departamento, fecha, ocupacionEstimadaconsolidado, ocupacionEstimadahotel, ocupacionEstimadahostal, ocupacionEstimadaotros FROM occupation_predictions WHERE departamento = :departamento")
    suspend fun getPredictionsByRegion(departamento: String): List<OccupationResponse>

}