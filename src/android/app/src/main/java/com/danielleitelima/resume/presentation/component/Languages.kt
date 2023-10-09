
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.Language
import com.danielleitelima.resume.domain.model.LanguageLevel
import com.danielleitelima.resume.presentation.component.LanguageView
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun Languages(languages: List<Language>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Languages",
            style = MaterialTheme.typographyFamily.displayXs.medium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))

        Column {
            languages.forEach { language ->
                Spacer(modifier = Modifier.height(12.dp))
                LanguageView(
                    language = language,
                    color = Color(language.color),
                )
            }
        }

    }
}

@Preview
@Composable
fun LanguagesPreview() {
    val languages = listOf(
        Language(
            name = "Portuguese",
            level = LanguageLevel.FLUENT,
            description = "Lorem ipsum dolor sit amet.",
            color = Color.Green.value,
        ),
        Language(
            name = "English",
            level = LanguageLevel.FLUENT,
            description = "Lorem ipsum dolor sit amet.",
            color = Color.Red.value,
        ),
        Language(
            name = "German",
            level = LanguageLevel.BASIC,
            description = "Lorem ipsum dolor sit amet.",
            color = Color.Yellow.value,
        ),
    )

    Languages(languages = languages)
}
