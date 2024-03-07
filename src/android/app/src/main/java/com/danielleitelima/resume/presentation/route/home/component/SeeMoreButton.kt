package com.danielleitelima.resume.presentation.route.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SeeMoreButton(
    modifier: Modifier = Modifier,
    onSeeMore: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable(
                onClick = onSeeMore,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ),
    ){
        Text(
            text = "See more",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
fun SeeMoreButtonPreview() {
    SeeMoreButton()
}