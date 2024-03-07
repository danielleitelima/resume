package com.danielleitelima.resume.presentation.route.home

import ArticleSection
import CodeSampleSection
import EducationSection
import JobExperienceSection
import LanguageSection
import SkillSection
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.CodeSample
import com.danielleitelima.resume.domain.model.Resume
import com.danielleitelima.resume.presentation.route.home.component.Footer
import com.danielleitelima.resume.presentation.route.home.component.IntroductionSection
import com.danielleitelima.resume.presentation.route.home.component.PersonalDataSection

@Composable
fun HomeScreen(
    resume: Resume
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),

        ) {
        Column(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 0.dp)
        ) {

            val codeSamples = listOf(
                CodeSample(
                    title = "Assisted chatting",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl.",
                    imageUrl = "https://danielleitelima.github.io/resume/assets/illustration_coding_sample_placeholder.png",
                    tag = "Latest"
                ),
                CodeSample(
                    title = "Local notifications",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl.",
                    imageUrl = "https://danielleitelima.github.io/resume/assets/illustration_coding_sample_placeholder.png",
                )
            )


            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                PersonalDataSection(
                    personalData = resume.personalData
                )
                Spacer(modifier = Modifier.height(32.dp))
                IntroductionSection(
                    introduction = resume.introduction
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            CodeSampleSection(codeSamples = codeSamples)
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {

                SkillSection(resume.skills)
                Spacer(modifier = Modifier.height(32.dp))
                JobExperienceSection(jobExperiences = resume.jobExperiences)
                Spacer(modifier = Modifier.height(32.dp))
                LanguageSection(languages = resume.languages)
                Spacer(modifier = Modifier.height(32.dp))
                EducationSection(resume.educationBackground)
                Spacer(modifier = Modifier.height(32.dp))

            }

            ArticleSection(
                articles = resume.articles,
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        Footer()
    }
}
