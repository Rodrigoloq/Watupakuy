package com.rodrigoloq.watupakuy.data.room.entities.occupancy

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "occupancy", indices = [Index("department")])
data class OccupancyTable(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                          val department: String,
                          val date: String,
                          val occupancyPercentage: Double,
                          val clampedToRange: Boolean,
                          val occupancyPercentageHotel: Double,
                          val occupancyPercentageHostal: Double,
                          val occupancyPercentageUnclassified: Double,
                          val lastObservedDate: String)
