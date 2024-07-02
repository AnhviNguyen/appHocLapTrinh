package com.example.mobilefinalproject.config

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.ZoneOffset
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun TimeAgo(dateTimeString: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val pastDateTime = ZonedDateTime.parse(dateTimeString, formatter)
    val currentDateTime = ZonedDateTime.now(ZoneOffset.UTC)

    val minutes = ChronoUnit.MINUTES.between(pastDateTime, currentDateTime)
    val hours = ChronoUnit.HOURS.between(pastDateTime, currentDateTime)
    val days = ChronoUnit.DAYS.between(pastDateTime, currentDateTime)
    val months = ChronoUnit.MONTHS.between(pastDateTime, currentDateTime)
    val years = ChronoUnit.YEARS.between(pastDateTime, currentDateTime)

    return when {
        years > 0 -> "$years years ago"
        months > 0 -> "$months months ago"
        days > 0 -> "$days days ago"
        hours > 0 -> "$hours hours ago"
        minutes > 0 -> "$minutes minutes ago"
        else -> "just now"
    }
}
