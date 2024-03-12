package com.danielleitelima.resume.presentation.route.home

import ArticleSection
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

//            TODO: Add code samples

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

            if (resume.articles.isNotEmpty()) {
                ArticleSection(
                    articles = resume.articles,
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        Footer(
            emailAddress = resume.personalData.emailAddress,
            linkedinUrl = resume.personalData.linkedinUrl,
            githubUrl = resume.personalData.githubUrl
        )
    }
}
