package com.rodrigoloq.watupakuy.data.refrofit.entities.nearbyplaces

import com.google.gson.annotations.SerializedName

data class Properties(@SerializedName("place_id") val placeId: String? = null,
                      val name: String? = null,
                      val lat: Double? = null,
                      val lon: Double? = null,
                      val formatted: String? = null,
                      @SerializedName("address_line1") val addressLine1: String? = null,
                      @SerializedName("address_line2") val addressLine2: String? = null,
                      val categories: List<String> = listOf(),
                      val distance: Int? = null,
                      val website: String? = null,
                      @SerializedName("opening_hours") val openingHours: String? = null
    )

