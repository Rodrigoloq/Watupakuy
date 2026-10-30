package com.rodrigoloq.watupakuy.data.refrofit.entities.occupation

import com.google.gson.annotations.SerializedName

data class OccupancyByClass(val clase: String,
                            @SerializedName("prediccion_tnoh") val percent: Double)
