
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
import com.danielleitelima.resume.domain.model.JobExperience
import com.danielleitelima.resume.domain.model.Role
import com.danielleitelima.resume.presentation.route.home.component.SeeMoreButton

@Composable
fun JobExperienceSection(
    jobExperiences: List<JobExperience>,
    onSeeMore: () -> Unit = {},
){
    Column {
        Text(
            text = "Experience",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(24.dp))
        jobExperiences.forEachIndexed { index, experience ->
            JobExperienceItem(
                jobExperience = experience,
                isFirst = index == 0,
            )
        }
        if (jobExperiences.size > 10) {
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
private fun JobExperienceSectionPreview() {
    val jobExperiences = listOf(
        JobExperience(
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
        JobExperience(
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

    JobExperienceSection(jobExperiences = jobExperiences)
}
