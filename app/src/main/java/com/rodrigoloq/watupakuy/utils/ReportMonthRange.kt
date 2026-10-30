package com.rodrigoloq.watupakuy.utils

import java.time.YearMonth

data class ReportMonthRange(val first: YearMonth, val last: YearMonth) {

    init {
        require(!last.isBefore(first)) { "El último mes ($last) no puede ser anterior al primero ($first)" }
    }

    operator fun contains(month: YearMonth): Boolean = month in first..last

    val years: IntRange get() = first.year..last.year
}