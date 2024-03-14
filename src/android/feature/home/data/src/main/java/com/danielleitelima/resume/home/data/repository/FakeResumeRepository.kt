package com.danielleitelima.resume.home.data.repository

import androidx.compose.ui.graphics.Color
import com.danielleitelima.resume.home.domain.model.Article
import com.danielleitelima.resume.home.domain.model.Company
import com.danielleitelima.resume.home.domain.model.Education
import com.danielleitelima.resume.home.domain.model.Introduction
import com.danielleitelima.resume.home.domain.model.JobExperience
import com.danielleitelima.resume.home.domain.model.Language
import com.danielleitelima.resume.home.domain.model.LanguageLevel
import com.danielleitelima.resume.home.domain.model.PersonalData
import com.danielleitelima.resume.home.domain.model.Resume
import com.danielleitelima.resume.home.domain.model.Role
import com.danielleitelima.resume.home.domain.model.Skill
import com.danielleitelima.resume.home.domain.repository.ResumeRepository

class FakeResumeRepository: ResumeRepository {

    override suspend fun get(): Resume {
        val personalData = PersonalData(
            name = "Daniel Leite Lima",
            description = "Mobile software engineer\nAndroid | iOS | KMM | Flutter | React Native",
            location = "Berlin, Germany",
            photoUrl = "https://danielleitelima.github.io/resume/assets/profile_photo.jpg",
            emailAddress = "limaleite.daniel@gmail.com",
            linkedinUrl = "https://www.linkedin.com/in/danielleitelima/",
            githubUrl = "https://github.com/danielleitelima/resume"
        )

        val introduction = Introduction(
            title = "Hi there!",
            description = "Lorem ipsum dolor sit amet consectetur. Felis a amet scelerisque semper eu luctus quam in fusce. Mattis vitae amet viverra purus tortor varius eget diam amet. Nullam non magna est viverra non. Quis semper id iaculis enim convallis euismod.",
        )

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

        return Resume(
            personalData = personalData,
            introduction = introduction,
            skills = skills,
            jobExperiences = jobExperiences,
            languages = languages,
            educationBackground = educationList,
            articles = articles,
        )
    }
}