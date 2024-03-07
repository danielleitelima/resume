package com.danielleitelima.resume.presentation.route.home.component

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
fun LightBackground() {
    val size = 2000.dp
    val radian = Math.toRadians(64.0)

    val startX = (kotlin.math.cos(radian) * size.value).toFloat()
    val startY = (kotlin.math.sin(radian) * size.value).toFloat()
    val endX = startX * -1
    val endY = startY * -1

    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF5F5F5),
            Color(0xFFDDF0EA),
            Color(0xFFC7D1D8),
            Color(0xFFCCDAE4),
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