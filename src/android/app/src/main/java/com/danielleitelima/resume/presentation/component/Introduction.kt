package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Introduction(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = description,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Preview
@Composable
fun IntroductionPreview() {
    Introduction(
        title = "Hi there!",
        description = "Lorem ipsum dolor sit amet consectetur. Felis a amet scelerisque semper eu luctus quam in fusce. Mattis vitae amet viverra purus tortor varius eget diam amet. Nullam non magna est viverra non. Quis semper id iaculis enim convallis euismod.",
    )
}