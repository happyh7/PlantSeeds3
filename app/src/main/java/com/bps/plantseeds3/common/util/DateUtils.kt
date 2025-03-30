package com.bps.plantseeds3.common.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun formatDate(date: LocalDate): String {
        return date.format(formatter)
    }

    fun parseDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, formatter)
    }

    fun getDaysBetween(startDate: LocalDate, endDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }

    fun isDateInRange(date: LocalDate, startDate: LocalDate, endDate: LocalDate): Boolean {
        return !date.isBefore(startDate) && !date.isAfter(endDate)
    }
} 