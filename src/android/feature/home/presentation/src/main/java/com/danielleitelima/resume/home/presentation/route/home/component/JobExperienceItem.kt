
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.home.domain.model.Company
import com.danielleitelima.resume.home.domain.model.JobExperience
import com.danielleitelima.resume.home.domain.model.Role
import com.danielleitelima.resume.home.presentation.route.home.component.CompanyItem
import com.danielleitelima.resume.home.presentation.route.home.component.RoleItem
import com.danielleitelima.resume.home.presentation.route.home.component.TimeLinePoint

@Composable
fun JobExperienceItem(
    modifier: Modifier = Modifier,
    jobExperience: JobExperience,
    isFirst: Boolean = false,
){
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
    ) {
        TimeLinePoint(isFirst = isFirst)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            CompanyItem(company = jobExperience.company)
            jobExperience.roles.forEach {
                Spacer(modifier = Modifier.height(12.dp))
                RoleItem(role = it)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
private fun JobExperienceItemPreview() {
    JobExperienceItem(
        jobExperience = JobExperience(
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
}
