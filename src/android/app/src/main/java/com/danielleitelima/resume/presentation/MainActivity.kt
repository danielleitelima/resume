package com.danielleitelima.resume.presentation

import ArticleSection
import CodeSampleSection
import EducationSection
import ExperienceSection
import LanguageSection
import SkillSection
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.domain.model.Article
import com.danielleitelima.resume.domain.model.CodeSample
import com.danielleitelima.resume.domain.model.Company
import com.danielleitelima.resume.domain.model.Education
import com.danielleitelima.resume.domain.model.Experience
import com.danielleitelima.resume.domain.model.Language
import com.danielleitelima.resume.domain.model.LanguageLevel
import com.danielleitelima.resume.domain.model.Role
import com.danielleitelima.resume.domain.model.Skill
import com.danielleitelima.resume.presentation.component.Footer
import com.danielleitelima.resume.presentation.component.Header
import com.danielleitelima.resume.presentation.component.Introduction
import com.danielleitelima.resume.presentation.theme.AppTheme

// TODO: Get mocked content from fake repository

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val scrollableState = rememberScrollState()
                        CompositionLocalProvider(
                            LocalOverscrollConfiguration provides null
                        ) {
                            Column(
                                modifier = Modifier
                                    .verticalScroll(scrollableState),

                                ) {
                                Column(
                                    modifier = Modifier
                                        .padding(top = 32.dp, bottom = 0.dp)
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 24.dp)
                                    ) {
                                        Header(
                                            name = "Daniel Leite Lima",
                                            description = "Mobile software engineer\nAndroid | iOS | KMM | Flutter | React Native",
                                            location = "Berlin, Germany",
                                            photoUrl = "https://danielleitelima.github.io/resume/assets/profile_photo.jpg",
                                        )
                                        Spacer(modifier = Modifier.height(32.dp))
                                        Introduction(
                                            title = "Hi there!",
                                            description = "Lorem ipsum dolor sit amet consectetur. Felis a amet scelerisque semper eu luctus quam in fusce. Mattis vitae amet viverra purus tortor varius eget diam amet. Nullam non magna est viverra non. Quis semper id iaculis enim convallis euismod.",
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(32.dp))

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

                                    CodeSampleSection(codeSamples = codeSamples)
                                    Spacer(modifier = Modifier.height(32.dp))

                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 24.dp)
                                    ) {
                                        val skills = listOf(
                                            Skill(
                                                description = "GraphQL",
                                                "https://danielleitelima.github.io/resume/assets/ic_android.png"
                                            ),
                                            Skill(
                                                description = "Jetpack Compose",
                                                "https://danielleitelima.github.io/resume/assets/ic_android.png"
                                            ),
                                            Skill(
                                                description = "Room DB",
                                                "https://danielleitelima.github.io/resume/assets/ic_android.png"
                                            ),
                                            Skill(
                                                description = "Kotlin",
                                                "https://danielleitelima.github.io/resume/assets/ic_android.png"
                                            ),
                                            Skill(
                                                description = "KMM",
                                                "https://danielleitelima.github.io/resume/assets/ic_android.png"
                                            ),
                                        )

                                        SkillSection(skills)
                                        Spacer(modifier = Modifier.height(32.dp))

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
                                        Spacer(modifier = Modifier.height(32.dp))

                                        val languages = listOf(
                                            Language(
                                                name = "Portuguese",
                                                level = LanguageLevel.FLUENT,
                                                description = "Lorem ipsum dolor sit amet.",
                                                color = Color.Green.value,
                                            ),
                                            Language(
                                                name = "English",
                                                level = LanguageLevel.FLUENT,
                                                description = "Lorem ipsum dolor sit amet.",
                                                color = Color.Red.value,
                                            ),
                                            Language(
                                                name = "German",
                                                level = LanguageLevel.BASIC,
                                                description = "Lorem ipsum dolor sit amet.",
                                                color = Color.Yellow.value,
                                            ),
                                        )

                                        LanguageSection(languages = languages)
                                        Spacer(modifier = Modifier.height(32.dp))

                                        val educationList = listOf(
                                            Education(
                                                title = "Bachelor of Computer Science",
                                                institution = "University of São Paulo",
                                                period = "2017 - 2021",
                                                location = "São Paulo, Brazil",
                                            ),
                                            Education(
                                                title = "Bachelor of Computer Science",
                                                institution = "University of São Paulo",
                                                period = "2017 - 2021",
                                                location = "São Paulo, Brazil",
                                            ),
                                            Education(
                                                title = "Bachelor of Computer Science",
                                                institution = "University of São Paulo",
                                                period = "2017 - 2021",
                                                location = "São Paulo, Brazil",
                                            ),
                                        )

                                        EducationSection(educationList)
                                        Spacer(modifier = Modifier.height(32.dp))

                                    }

                                    val articles = listOf(
                                        Article(
                                            title = "Local notifications",
                                            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl.",
                                            imageUrl = "https://danielleitelima.github.io/resume/assets/illustration_coding_sample_placeholder.png",
                                            label = "28.05.2023 - 5 min read",
                                        ),
                                        Article(
                                            title = "Local notifications",
                                            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl.",
                                            imageUrl = "https://danielleitelima.github.io/resume/assets/illustration_coding_sample_placeholder.png",
                                            label = "28.05.2023 - 5 min read",
                                        ),
                                        Article(
                                            title = "Local notifications",
                                            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl. Sed euismod, diam quis aliquam faucibus, nisl quam aliquet nunc, quis aliquam nisl nunc eu nisl.",
                                            imageUrl = "https://danielleitelima.github.io/resume/assets/illustration_coding_sample_placeholder.png",
                                            label = "28.05.2023 - 5 min read",
                                        ),
                                    )

                                    ArticleSection(
                                        articles = articles,
                                    )
                                    Spacer(modifier = Modifier.height(32.dp))
                                }
                                Footer()
                            }
                        }
                    }
                }
            }
        }
    }
}