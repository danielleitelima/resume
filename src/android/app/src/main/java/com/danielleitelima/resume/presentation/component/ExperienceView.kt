
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
import com.danielleitelima.resume.domain.model.Company
import com.danielleitelima.resume.domain.model.Experience
import com.danielleitelima.resume.domain.model.Role
import com.danielleitelima.resume.presentation.component.CompanyView
import com.danielleitelima.resume.presentation.component.RoleView
import com.danielleitelima.resume.presentation.component.TimeLinePoint

@Composable
fun ExperienceView(
    experience: Experience,
    isFirst: Boolean = false,
    modifier: Modifier = Modifier,
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
            CompanyView(company = experience.company)
            experience.roles.forEach {
                Spacer(modifier = Modifier.height(12.dp))
                RoleView(role = it)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun ExperienceViewPreview() {
    ExperienceView(
        experience = Experience(
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
