package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FooterButton(
    icon: ImageVector
) {
    OutlinedButton(
        onClick = { /* Do something on click */ },
        shape = CircleShape,
        modifier = Modifier.size(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        contentPadding = PaddingValues(0.dp)
    ) {

        Icon(
            imageVector = icon,
            contentDescription = "Footer Button",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun FooterButtonPreview() {
    FooterButton(icon = Icons.Outlined.MailOutline)
}