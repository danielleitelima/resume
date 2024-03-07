package com.danielleitelima.resume.presentation.route.home.component

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
import com.danielleitelima.resume.domain.model.PersonalData

@Composable
fun PersonalDataSection(
    personalData: PersonalData
) {
    Column {
        Text(
            text = personalData.name,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = personalData.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(12.dp))
        IllustratedDescription(
            icon = Icons.Filled.LocationOn,
            description = personalData.location,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            ProfilePhoto(imageUrl = personalData.photoUrl, contentDescription = "Profile photo")
        }

    }
}

@Preview
@Composable
fun HeaderPreview() {
    val personalData = PersonalData(
        name = "Daniel Leite Lima",
        description = "Mobile software engineer\nAndroid | iOS | KMM | Flutter | React Native",
        location = "Berlin, Germany",
        photoUrl = "https://danielleitelima.github.io/resume/assets/profile_photo.jpg",
    )

    PersonalDataSection(
        personalData = personalData
    )
}