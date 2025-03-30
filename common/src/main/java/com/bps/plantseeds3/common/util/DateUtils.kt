package com.bps.plantseeds3.common.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun formatDate(date: LocalDate?): String {
        return date?.format(formatter) ?: ""
    }

    fun parseDate(dateString: String?): LocalDate? {
        return try {
            dateString?.let { LocalDate.parse(it, formatter) }
        } catch (e: Exception) {
            null
        }
    }

    fun convertToLocalDate(date: Date?): LocalDate? {
        return date?.toInstant()?.atZone(java.time.ZoneId.systemDefault())?.toLocalDate()
    }

    fun convertToDate(localDate: LocalDate?): Date? {
        return localDate?.atStartOfDay(java.time.ZoneId.systemDefault())?.toInstant()?.let { Date.from(it) }
    }

    fun getCurrentDate(): LocalDate {
        return LocalDate.now()
    }

    fun isDateValid(date: LocalDate?): Boolean {
        return date != null && !date.isBefore(LocalDate.now())
    }
} 