package com.rodrigoloq.watupakuy.data.room.entities.tourism

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tourism", indices = [Index("department")])
data class TourismTable(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                        val department: String,
                        val date: String,
                        val title: String,
                        val description: String)
