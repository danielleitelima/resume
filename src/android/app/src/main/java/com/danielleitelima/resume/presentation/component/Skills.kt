
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.Skill
import com.danielleitelima.resume.presentation.component.SkillView
import com.danielleitelima.resume.presentation.theme.typographyFamily

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Skills(skills: List<Skill>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Skills",
            style = MaterialTheme.typographyFamily.displayXs.medium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(24.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            skills.forEach { skill ->
                SkillView(
                    modifier = Modifier.padding(bottom = 8.dp),
                    skill = skill,
                )
            }
        }
    }
}

@Preview
@Composable
fun SkillsPreview() {
    val skills = listOf(
        Skill(
            description = "GraphQL",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/GraphQL_Logo.svg/2048px-GraphQL_Logo.svg.png"
        ),
        Skill(
            description = "Jetpack Compose",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/GraphQL_Logo.svg/2048px-GraphQL_Logo.svg.png"
        ),
        Skill(
            description = "Room DB",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/GraphQL_Logo.svg/2048px-GraphQL_Logo.svg.png"
        ),
        Skill(
            description = "Kotlin",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/GraphQL_Logo.svg/2048px-GraphQL_Logo.svg.png"
        ),
        Skill(
            description = "KMM",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/GraphQL_Logo.svg/2048px-GraphQL_Logo.svg.png"
        ),
    )

    Skills(skills)
}
