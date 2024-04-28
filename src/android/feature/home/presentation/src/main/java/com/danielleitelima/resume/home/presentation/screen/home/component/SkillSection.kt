
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.home.domain.model.Skill
import com.danielleitelima.resume.home.presentation.screen.home.component.SeeMoreButton
import com.danielleitelima.resume.home.presentation.screen.home.component.SkillItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillSection(
    skills: List<Skill>,
    onSeeMore: () -> Unit = {},
){
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Skills",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(24.dp))
        FlowRow(
            modifier = Modifier.align(Alignment.Start),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            skills.forEach { skill ->
                SkillItem(
                    modifier = Modifier.padding(bottom = 8.dp),
                    skill = skill,
                )
            }
        }
        if (skills.size > 100) {
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
private fun SkillSectionPreview() {
    val skills = listOf(
        Skill(
            description = "GraphQL",
            "https://danielleitelima.github.io/resume/assets/ic_code.svg"
        ),
        Skill(
            description = "Jetpack Compose",
            "https://danielleitelima.github.io/resume/assets/ic_code.svg"
        ),
        Skill(
            description = "Room DB",
            "https://danielleitelima.github.io/resume/assets/ic_code.svg"
        ),
        Skill(
            description = "Kotlin",
            "https://danielleitelima.github.io/resume/assets/ic_code.svg"
        ),
        Skill(
            description = "KMM",
            "https://danielleitelima.github.io/resume/assets/ic_code.svg"
        ),
    )

    SkillSection(skills)
}
