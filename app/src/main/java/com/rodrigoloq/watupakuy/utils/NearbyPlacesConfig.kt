package com.rodrigoloq.watupakuy.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Attractions
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Museum
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class PlaceCategoryGroup (val name: String,
                               val label: String,
                               val icon: ImageVector,
                               val categories: String)

val PlaceCategoryGroups = listOf(
    PlaceCategoryGroup("ATTRACTIONS",
        "Atracciones",
        Icons.Outlined.Attractions,
        "tourism.sights,tourism.attraction"),
    PlaceCategoryGroup("ACCOMMODATION",
        "Alojamiento",
        Icons.Outlined.Bed,
        "accommodation"),
    PlaceCategoryGroup("FOOD",
        "Restaurantes y cafés",
        Icons.Outlined.Restaurant,
        "catering.restaurant,catering.cafe"),
    PlaceCategoryGroup("SUPERMARKETS",
        "Supermercados",
        Icons.Outlined.ShoppingCart,
        "commercial.supermarket"),
    PlaceCategoryGroup("HEALTH",
        "Salud",
        Icons.Outlined.LocalHospital,
        "healthcare.hospital,healthcare.clinic_or_praxis,healthcare.pharmacy"),
    PlaceCategoryGroup("ENTERTAINMENT",
        "Entretenimiento",
        Icons.Outlined.Museum,
        "entertainment")
)
