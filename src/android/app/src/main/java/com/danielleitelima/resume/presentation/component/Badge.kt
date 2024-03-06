package com.danielleitelima.resume.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    textColor: Color = Color.White,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(999.dp),
        colors = CardDefaults.cardColors(
            containerColor = color,
        ),
    ){
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typographyFamily.text2xs.semiBold,
                modifier = Modifier.align(Alignment.Center),
            )
        }

    }
}

@Preview
@Composable
fun BadgePreview() {
    Badge(text = "Kotlin")
}