package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun Introduction(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            style = MaterialTheme.typographyFamily.displayXs.regular,
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            style = MaterialTheme.typographyFamily.textS.light,
            text = description,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f))
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