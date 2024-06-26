package com.danielleitelima.resume.home.presentation.screen.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CodeSampleItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    imageUrl: String,
    tag: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(210.dp)
            .width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = onClick
    ) {

        Column {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(imageUrl).build(),
                    contentDescription = "Sample image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(125.dp)
                )

                if (tag != null) {
                    Badge(
                        text = tag,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview
@Composable
fun SampleCardPreview() {
    CodeSampleItem(
        title = "Fibonacci's sequence",
        description = "Calculates the sequence for all supported long numbers.Calculates the sequence for all supported long numbers.Calculates the sequence for all supported long numbers.Calculates the sequence for all supported long numbers.",
        imageUrl = "https://danielleitelima.github.io/resume/assets/ic_code.svg",
        tag = "Badge",
        onClick = {}
    )
}