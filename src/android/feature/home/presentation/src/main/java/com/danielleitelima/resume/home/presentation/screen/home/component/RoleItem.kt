package com.danielleitelima.resume.home.presentation.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.home.domain.model.Role

@Composable
fun RoleItem(
    role: Role,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = role.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = role.period,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            style = MaterialTheme.typography.bodySmall,
            text = role.description,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun RoleItemPreview() {
    Role(
        name = "Android Engineer",
        period = "August 2021 - August 2022",
        description = "Lorem ipsum dolor sit amet consectetur. Cum porttitor aliquam nisl pharetra. Hendrerit interdum felis accumsan nibh quisque nulla pulvinar tincidunt ornare. Nibh porttitor tristique facilisis sed augue volutpat vulputate. Nibh lectus volutpat cursus mattis amet imperdiet aenean."
    )
}