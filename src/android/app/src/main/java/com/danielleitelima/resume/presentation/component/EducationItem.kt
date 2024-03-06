package com.danielleitelima.resume.presentation.component

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
import com.danielleitelima.resume.domain.model.Education
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun EducationItem(
    education: Education,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ){
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = education.title,
                style = MaterialTheme.typographyFamily.textM.medium,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = education.institution,
                style = MaterialTheme.typographyFamily.textM.light,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            IllustratedDescription(
                icon = Icons.Filled.DateRange,
                description = education.period,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            IllustratedDescription(
                icon = Icons.Filled.LocationOn,
                description = education.location,
                color = MaterialTheme.colorScheme.onSecondary
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