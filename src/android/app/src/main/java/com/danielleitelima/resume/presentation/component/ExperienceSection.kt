
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
import com.danielleitelima.resume.domain.model.Company
import com.danielleitelima.resume.domain.model.Experience
import com.danielleitelima.resume.domain.model.Role
import com.danielleitelima.resume.presentation.component.SeeMoreButton
import com.danielleitelima.resume.presentation.theme.typographyFamily

@Composable
fun ExperienceSection(
    experiences: List<Experience>,
    onSeeMore: () -> Unit = {},
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Experience",
            style = MaterialTheme.typographyFamily.displayXs.medium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(24.dp))
        experiences.forEachIndexed { index, experience ->
            ExperienceView(
                experience = experience,
                isFirst = index == 0,
            )
        }
        if (experiences.size > 1) {
            Spacer(modifier = Modifier.height(16.dp))
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
fun ExperiencesPreview() {
    val experiences = listOf(
        Experience(
            company = Company(
                name = "MAYD: Meds at your doorstep",
                period = "1 year",
                location = "São Paulo, Brazil",
            ),
            roles = listOf(
                Role(
                    name = "Android Developer",
                    period = "August 2022 - Present",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl."
                ),
                Role(
                    name = "Android Developer",
                    period = "August 2021 - August 2022",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl."
                )
            )
        ),
        Experience(
            company = Company(
                name = "MAYD: Meds at your doorstep",
                period = "1 year",
                location = "São Paulo, Brazil",
            ),
            roles = listOf(
                Role(
                    name = "Android Developer",
                    period = "August 2022 - Present",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl."
                ),
                Role(
                    name = "Android Developer",
                    period = "August 2021 - August 2022",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl."
                )
            )
        )
    )

    ExperienceSection(experiences = experiences)
}
