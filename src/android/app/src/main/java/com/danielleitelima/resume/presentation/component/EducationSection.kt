
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
import com.danielleitelima.resume.presentation.component.EducationItem
import com.danielleitelima.resume.presentation.component.SeeMoreButton

@Composable
fun EducationSection(
    educationList: List<Education>,
    onSeeMore: () -> Unit = {},
){
    Column {
        Text(
            text = "Education",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(12.dp))

        educationList.forEach { education ->
            Spacer(modifier = Modifier.height(12.dp))
            EducationItem(
                education
            )
        }

        if (educationList.size > 10) {
            Spacer(modifier = Modifier.height(24.dp))
            SeeMoreButton(
                modifier = Modifier.align(Alignment.End),
            ){
                onSeeMore()
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

    EducationSection(educationList)
}
