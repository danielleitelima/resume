package com.danielleitelima.resume.chat.presentation.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.chat.domain.Article
import com.danielleitelima.resume.chat.presentation.R
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension
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

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (String) -> Unit = {},
) {
    Card(
        modifier = modifier
            .height(140.dp),
        shape = RoundedCornerShape(Dimension.CornerRadius.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimension.Elevation.dp
        ),
        onClick = { onClick(article.id) },
    ) {
        Column(
            modifier = Modifier.padding(Dimension.Spacing.M.dp),
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(Dimension.Spacing.XS.dp))
            Text(
                text = article.teaser,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                text = stringResource(
                    R.string.message_detail_read_time,
                    article.lastUpdate.toArticleDate(),
                    article.readTime
                ),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
            )
        }
    }
}