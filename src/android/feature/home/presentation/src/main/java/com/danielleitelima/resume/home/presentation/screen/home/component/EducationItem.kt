package com.danielleitelima.resume.home.presentation.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.home.domain.model.Education

@Composable
fun EducationItem(
    education: Education,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        ),
    ){
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = education.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = education.institution,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            IllustratedDescription(
                icon = Icons.Filled.DateRange,
                description = education.period,
            )
            Spacer(modifier = Modifier.height(8.dp))
            IllustratedDescription(
                icon = Icons.Filled.LocationOn,
                description = education.location,
            )
        }
    }
}

@Preview
@Composable
fun EducationItemPreview() {
    EducationItem(
        Education(
            title = "Systems analysis and development",
            institution = "UNESA",
            period = "2019 - 2021",
            location = "Curitiba, Brazil",
        )
    )
}