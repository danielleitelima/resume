package com.danielleitelima.resume.foundation.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.danielleitelima.resume.foundation.presentation.foundation.theme.Dimension

@Composable
fun Clickable(
    onClick: () -> Unit,
    indicationColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimension.CornerRadius.dp))
            .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = indicationColor),
            onClick = onClick
        )
    ) {
        content()
    }
}