package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.Language
import com.danielleitelima.resume.domain.model.LanguageLevel
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun LanguageItem(
    modifier: Modifier = Modifier,
    language: Language,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = language.name,
                    style = MaterialTheme.typographyFamily.textS.regular,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = language.description,
                    style = MaterialTheme.typographyFamily.textXs.light,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f),
                )
            }
            LanguageLevelIndicator(level = language.level)
        }
    }
}

@Preview
@Composable
fun LanguagePreview() {
    LanguageItem(
        language = Language(
            name = "Portuguese",
            level = LanguageLevel.FLUENT,
            description = "Lorem ipsum dolor sit amet.",
            color = Color.Red.value,
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}