package com.example.eatwise.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateHelper {
    fun calculateAge(birthDateString: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val birthDate: Date = dateFormat.parse(birthDateString) ?: return -1

        val birthCalendar = Calendar.getInstance().apply { time = birthDate }
        val todayCalendar = Calendar.getInstance()

        var age = todayCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

        if (todayCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }
}