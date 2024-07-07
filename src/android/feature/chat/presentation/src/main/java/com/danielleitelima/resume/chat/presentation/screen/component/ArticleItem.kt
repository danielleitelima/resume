package com.danielleitelima.resume.chat.presentation.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.danielleitelima.resume.chat.presentation.R
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun Long.toArticleDate(): String {
    val instant = Instant.ofEpochMilli(this)
    val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    val now = ZonedDateTime.now()

    val daysBetween = ChronoUnit.DAYS.between(zonedDateTime.toLocalDate(), now.toLocalDate())
    return if (daysBetween == 0L) {
        val hoursBetween = ChronoUnit.HOURS.between(zonedDateTime, now)
        if (hoursBetween == 0L) stringResource(R.string.article_date_recently) else stringResource(
            R.string.article_date_hours_ago,
            hoursBetween
        )
    } else if (daysBetween in 1..6) {
        stringResource(R.string.article_date_days_ago, daysBetween)
    } else {
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault())
        zonedDateTime.format(formatter)
    }
}
