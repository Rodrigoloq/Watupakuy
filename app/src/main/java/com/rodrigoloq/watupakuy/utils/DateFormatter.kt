package com.rodrigoloq.watupakuy.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {

    val monthMapper = mapOf(
        1 to "Enero",
        2 to "Febrero",
        3 to "Marzo",
        4 to "Abril",
        5 to "Marzo",
        6 to "Junio",
        7 to "Julio",
        8 to "Agosto",
        9 to "Septiembre",
        10 to "Octubre",
        11 to "Noviembre",
        12 to "Diciembre"
    )

    fun dateFormatter(date: String): String?{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatedDate = LocalDate.parse(date, formatter)

        return monthMapper[formatedDate.month.value]
    }
}