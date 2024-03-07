package com.danielleitelima.resume.presentation.route.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TimeLinePoint(
    modifier: Modifier = Modifier,
    isFirst: Boolean = false,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(16.dp)
            .padding(top = if (isFirst) 4.dp else 0.dp)
    ) {
        val modifierLine = Modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.onSurface)
            .align(Alignment.Center)

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.TopStart)
                .padding(top = if (isFirst.not()) 7.dp else 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.onSurface, CircleShape)
            )
        }
        Box(modifierLine)
    }
}

@Preview
@Composable
fun TimeLinePointPreview() {
    TimeLinePoint()
}