package com.danielleitelima.resume.chat.presentation.screen.component

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long?.toFormattedTime(): String {
    if (this == null) return ""

    val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
    return localDateTime.format(formatter)
}