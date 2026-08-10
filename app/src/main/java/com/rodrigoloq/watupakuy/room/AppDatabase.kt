package com.rodrigoloq.watupakuy.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigoloq.watupakuy.room.entities.OccupationPredictions

@Database(
    entities = [OccupationPredictions::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun occupationPredictionsDAO(): OccupationPredictionsDAO
}