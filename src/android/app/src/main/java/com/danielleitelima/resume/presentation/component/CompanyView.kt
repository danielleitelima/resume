package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.Company
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun CompanyView(
    company: Company,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(8f),
                text = company.name,
                style = MaterialTheme.typographyFamily.textXl.regular,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                modifier = Modifier.weight(2f),
                text = company.period,
                style = MaterialTheme.typographyFamily.textXs.light,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        IllustratedDescription(
            icon = Icons.Filled.LocationOn,
            description = company.location,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview
@Composable
fun CompanyPreview() {
    Company(
        name = "MAYD: Meds at your doorstep",
        period = "1 year",
        location = "Berlin, Germany",
    )
}