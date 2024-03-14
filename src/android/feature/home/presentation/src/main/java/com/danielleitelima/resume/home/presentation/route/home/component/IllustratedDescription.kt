package com.danielleitelima.resume.home.presentation.route.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun IllustratedDescription(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    imageUrl: String? = null,
    description: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = color,
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
            )
        }
        if (imageUrl != null) {
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).build(imageUrl),
                    contentDescription = "Illustration",
                    contentScale = ContentScale.Inside
                )
            }
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            style = textStyle,
            text = description,
            color = color,
        )
    }
}

@Preview
@Composable
fun IllustratedDescriptionPreview() {
    Column {
        IllustratedDescription(
            icon = Icons.Filled.LocationOn,
            description = "Mountain View, CA"
        )
        Spacer(modifier = Modifier.height(8.dp))
        IllustratedDescription(
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkDjkXOS9cHN3Qk47-8ZOhrnay_1_QaPe4V7mS-YA&s",
            description = "Mountain View, CA"
        )
    }
}