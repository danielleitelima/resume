package com.danielleitelima.resume.presentation.route.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Pill(color: Color, height: Dp = 3.dp, width: Dp = 10.dp) {
    Canvas(
        modifier = Modifier
            .height(height)
            .width(width)
    ) {
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(height.toPx() / 2, height.toPx() / 2),
            size = Size(width.toPx(), height.toPx())
        )
    }
}

@Preview
@Composable
fun PillPreview() {
    Pill(color = Color.Green)
}