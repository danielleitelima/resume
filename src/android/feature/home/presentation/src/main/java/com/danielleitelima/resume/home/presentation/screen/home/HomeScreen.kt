package com.danielleitelima.resume.home.presentation.screen.home

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.danielleitelima.resume.foundation.presentation.foundation.LocalNavHostController
import com.danielleitelima.resume.foundation.presentation.foundation.Route
import com.danielleitelima.resume.foundation.presentation.foundation.Screen
import com.danielleitelima.resume.foundation.presentation.foundation.getKoinInstance
import com.danielleitelima.resume.foundation.presentation.foundation.navigate
import com.danielleitelima.resume.foundation.presentation.foundation.rememberViewModel
import com.danielleitelima.resume.foundation.presentation.route.home.Home
import com.danielleitelima.resume.home.presentation.screen.home.component.CodeSample
import com.danielleitelima.resume.home.presentation.screen.home.component.CodeSampleSection
import com.danielleitelima.resume.home.presentation.screen.home.component.Footer
import com.danielleitelima.resume.home.presentation.screen.home.component.IntroductionSection
import com.danielleitelima.resume.home.presentation.screen.home.component.PersonalDataSection
import com.danielleitelima.resume.foundation.presentation.route.chat.Home as ChatHome

object HomeScreen : Screen {
    private const val CODE_SAMPLE_PLACEHOLDER_URL = "https://danielleitelima.github.io/resume/assets/illustration_coding_sample_placeholder.png"

    override val route: Route
        get() = Home

    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val viewModel: HomeViewModel = rememberViewModel { getKoinInstance() }
        val state by viewModel.state.collectAsState()

        state.resume?.let { resume ->
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

                    val navController = LocalNavHostController.current

                    val codeSamples = listOf(
                        CodeSample(
                            title = "Assisted chatting",
                            description = "A method for learning languages through simulated chats.",
                            imageUrl = CODE_SAMPLE_PLACEHOLDER_URL,
                            tag = null,
                            onClick = {
                                navController.navigate(ChatHome)
                            }
                        ),
                    )

                    CodeSampleSection(
                        codeSamples = codeSamples
                    )
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

    }
}
