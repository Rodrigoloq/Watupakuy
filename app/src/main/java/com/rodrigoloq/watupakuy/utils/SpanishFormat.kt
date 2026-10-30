package com.rodrigoloq.watupakuy.utils

import java.math.RoundingMode
import java.text.NumberFormat
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

val SpanishPeru: Locale = Locale.forLanguageTag("es-PE")

private const val MONTH_ABBREVIATION_LENGTH = 3
private const val METERS_PER_KILOMETER = 1000

private val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", SpanishPeru)

/** "diciembre 2026" -> "Diciembre 2026". */
fun YearMonth.toSpanishTitle(): String =
    format(monthYearFormatter).replaceFirstChar { it.titlecase(SpanishPeru) }

/** Para usar dentro de una frase: "junio 2025". */
fun YearMonth.toSpanishText(): String = format(monthYearFormatter)

/** "Diciembre". */
fun YearMonth.monthNameTitle(): String =
    month.getDisplayName(TextStyle.FULL_STANDALONE, SpanishPeru).replaceFirstChar { it.titlecase(SpanishPeru) }

/** Abreviatura de 3 letras sin punto: "Ene", "Set", "Dic" (es-PE: "setiembre"). */
fun YearMonth.monthAbbreviation(): String = monthNameTitle().take(MONTH_ABBREVIATION_LENGTH)

/** 350 -> "350 m"; 1250 -> "1.3 km" (es-PE usa punto decimal). */
fun formatDistance(meters: Int): String =
    if (meters < METERS_PER_KILOMETER) {
        "$meters m"
    } else {
        val format = NumberFormat.getNumberInstance(SpanishPeru).apply {
            minimumFractionDigits = 1
            maximumFractionDigits = 1
            roundingMode = RoundingMode.HALF_UP
        }
        "${format.format(meters.toDouble() / METERS_PER_KILOMETER)} km"
    }

/** Número con un decimal en formato local ("14.1", "1017.5") o null si no hay dato. */
fun formatOneDecimal(value: Double?): String? = value?.let {
    NumberFormat.getNumberInstance(SpanishPeru).apply {
        isGroupingUsed = false
        minimumFractionDigits = 1
        maximumFractionDigits = 1
        roundingMode = RoundingMode.HALF_UP
    }.format(it)
}

/** Número entero redondeado o null si no hay dato. */
fun formatInteger(value: Double?): String? = value?.let {
    NumberFormat.getIntegerInstance(SpanishPeru).format(Math.round(it))
}