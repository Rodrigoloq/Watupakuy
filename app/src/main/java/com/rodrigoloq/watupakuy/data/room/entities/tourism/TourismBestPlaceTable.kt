package com.rodrigoloq.watupakuy.data.room.entities.tourism

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rodrigoloq.watupakuy.data.room.entities.weather.WeatherTable

@Entity(tableName = "tourism_best_places", foreignKeys = [
    ForeignKey(
        entity = TourismTable::class,
        parentColumns =["id"],
        childColumns = ["tourismId"],
        onDelete = ForeignKey.CASCADE
    )
], indices = [Index("tourismId")])
data class TourismBestPlaceTable(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                                 val tourismId: Long,
                                 val title: String,
                                 val imageUrl: String)
