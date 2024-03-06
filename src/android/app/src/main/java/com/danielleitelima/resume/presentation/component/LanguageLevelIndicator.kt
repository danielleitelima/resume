package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.LanguageLevel

@Composable
fun LanguageLevelIndicator(
    level: LanguageLevel,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (i in 1..4) {
            Pill(
                color = if (i <= level.value) {
                    Color(0xFF32B566)
                } else {
                    MaterialTheme.colorScheme.tertiary
                }
            )
        }
    }
}

@Preview
@Composable
fun LanguageLevelIndicatorPreview() {
    LanguageLevelIndicator(
        level = LanguageLevel.FLUENT
    )
}

