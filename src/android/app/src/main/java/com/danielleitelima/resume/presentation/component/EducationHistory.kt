
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.Education
import com.danielleitelima.resume.presentation.component.EducationView
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun EducationHistory(educationList: List<Education>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Education",
            style = MaterialTheme.typographyFamily.displayXs.medium,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            educationList.forEach { education ->
                Spacer(modifier = Modifier.height(12.dp))
                EducationView(
                    education
                )
            }
        }

    }
}

@Preview
@Composable
fun EducationHistoryPreview() {
    val educationList = listOf(
        Education(
            title = "Bachelor of Computer Science",
            institution = "University of São Paulo",
            period = "2017 - 2021",
            location = "São Paulo, Brazil",
        ),
        Education(
            title = "Bachelor of Computer Science",
            institution = "University of São Paulo",
            period = "2017 - 2021",
            location = "São Paulo, Brazil",
        ),
        Education(
            title = "Bachelor of Computer Science",
            institution = "University of São Paulo",
            period = "2017 - 2021",
            location = "São Paulo, Brazil",
        ),
    )

    EducationHistory(educationList)
}
