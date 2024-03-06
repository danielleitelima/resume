package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.Role
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun RoleView(
    role: Role,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = role.name,
            style = MaterialTheme.typographyFamily.textM.semiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = role.period,
            style = MaterialTheme.typographyFamily.textXs.regular,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            style = MaterialTheme.typographyFamily.text2xs.regular,
            text = role.description,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun RolePreview() {
    Role(
        name = "Android Engineer",
        period = "August 2021 - August 2022",
        description = "Lorem ipsum dolor sit amet consectetur. Cum porttitor aliquam nisl pharetra. Hendrerit interdum felis accumsan nibh quisque nulla pulvinar tincidunt ornare. Nibh porttitor tristique facilisis sed augue volutpat vulputate. Nibh lectus volutpat cursus mattis amet imperdiet aenean."
    )
}