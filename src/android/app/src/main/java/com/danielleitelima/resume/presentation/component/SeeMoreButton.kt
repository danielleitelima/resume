package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun SeeMoreButton(
    modifier: Modifier = Modifier,
    onSeeMore: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable(
                onClick = onSeeMore,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ),
    ){
        Text(
            text = "See more",
            style = MaterialTheme.typographyFamily.textS.regular,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "See more",
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview
@Composable
fun SeeMoreButtonPreview() {
    SeeMoreButton()
}