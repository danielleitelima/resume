package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun Header(
    name: String,
    description: String,
    location: String,
    photoUrl: String,
) {
    Column {
        Text(
            text = name,
            style = MaterialTheme.typographyFamily.displayS.medium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = description,
            style = MaterialTheme.typographyFamily.textXs.light,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        IllustratedDescription(
            icon = Icons.Filled.LocationOn,
            description = location,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            ProfilePhoto(imageUrl = photoUrl, contentDescription = "Profile photo")
        }

    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(
        name = "Daniel Leite Lima",
        description = "Mobile software engineer\nAndroid | iOS | KMM | Flutter | React Native",
        location = "Berlin, Germany",
        photoUrl = "https://avatars.githubusercontent.com/u/16729547?v=4",
    )
}