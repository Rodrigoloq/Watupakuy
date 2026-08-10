package com.rodrigoloq.watupakuy.utils

object CategoryMapper {

    val categoriesMapper = mapOf(
        "Alojamiento" to "accommodation",
        "Natural" to "natural",
        "Transporte" to "public_transport",
        "Centro comercial" to "commercial.shopping_mall",
        "Supermercado" to "commercial.supermarket",
        "Entretenimiento" to "entertainment",
        "Sanidad" to "healthcare",
        "Bancos" to "service.financial",
        "Restaurantes" to "catering.restaurant"
    )

    fun getCategoryName(category: String): String{
        return categoriesMapper[category]!!
    }
}