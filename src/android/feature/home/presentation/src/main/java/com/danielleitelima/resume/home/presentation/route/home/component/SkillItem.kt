package com.danielleitelima.resume.home.presentation.route.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.home.domain.model.Skill

@Composable
fun SkillItem(
    skill: Skill,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ){
        IllustratedDescription(
            imageUrl = skill.imageUrl,
            description = skill.description,
            textStyle = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}

@Preview
@Composable
fun SkillPreview() {
    SkillItem(
        Skill(
            description = "Skill",
            "https://danielleitelima.github.io/resume/assets/ic_code.svg"
        )
    )
}