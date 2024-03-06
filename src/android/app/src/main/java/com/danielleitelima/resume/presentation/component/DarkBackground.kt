package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DarkBackground() {
    val size = 2000.dp
    val radian = Math.toRadians(64.0)

    val startX = (kotlin.math.cos(radian) * size.value).toFloat()
    val startY = (kotlin.math.sin(radian) * size.value).toFloat()
    val endX = startX * -1
    val endY = startY * -1

    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF242424),
            Color(0xFF15272B),
            Color(0xFF132737),
            Color(0xFF0B2543),
        ),
        start = Offset(startX, startY),
        end = Offset(endX, endY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
    )
}