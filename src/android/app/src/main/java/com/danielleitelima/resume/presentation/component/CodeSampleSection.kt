
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.CodeSample
import com.danielleitelima.resume.presentation.component.SampleCard
import com.danielleitelima.resume.presentation.component.SeeMoreButton
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun CodeSampleSection(
    codeSamples: List<CodeSample>,
    onSeeMore: () -> Unit = {},
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Coding style samples",
                style = MaterialTheme.typographyFamily.displayXs.medium.copy(textAlign = TextAlign.Center),
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                style = MaterialTheme.typographyFamily.textS.light,
                text = "For those who are interested, you can check my coding style with some samples. ",
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            codeSamples.forEach { codeSample ->
                SampleCard(
                    title = codeSample.title,
                    description = codeSample.description,
                    imageUrl = codeSample.imageUrl,
                    tag = codeSample.tag,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        if (codeSamples.size > 1) {
            Spacer(modifier = Modifier.height(16.dp))
            SeeMoreButton(
                modifier = Modifier.align(Alignment.End).padding(end = 24.dp),
            ){
                onSeeMore()
            }
        }
    }
}

@Preview
@Composable
fun CodeSampleSectionPreview() {
    val codeSamples = listOf(
        CodeSample(
            title = "Kotlin",
            description = "Kotlin is a cross-platform, statically typed, general-purpose programming language with type inference.",
            imageUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            tag = "Kotlin",
        ),
        CodeSample(
            title = "Java",
            description = "Java is a class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.",
            imageUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            tag = "Java",
        ),
        CodeSample(
            title = "Python",
            description = "Python is an interpreted high-level general-purpose programming language.",
            imageUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            tag = "Python",
        ),
    )
    CodeSampleSection(
        codeSamples = codeSamples,
    )
}
