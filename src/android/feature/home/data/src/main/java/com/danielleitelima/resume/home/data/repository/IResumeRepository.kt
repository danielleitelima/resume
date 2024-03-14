package com.danielleitelima.resume.home.data.repository

import com.danielleitelima.resume.home.data.datasource.remote.api.ResumeAPI
import com.danielleitelima.resume.home.data.datasource.remote.dto.ArticleDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.CompanyDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.EducationDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.IntroductionDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.JobExperienceDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.LanguageDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.PersonalDataDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.ResumeDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.RoleDTO
import com.danielleitelima.resume.home.data.datasource.remote.dto.SkillDTO
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

class IResumeRepository(
    private val resumeAPI: ResumeAPI
): ResumeRepository {

    override suspend fun get(): Resume {
        return resumeAPI.getResume().toDomainModel()
    }

    private fun ResumeDTO.toDomainModel() = Resume(
        personalData = personalData.toDomainModel(),
        introduction = introduction.toDomainModel(),
        skills = skills.map { it.toDomainModel() },
        jobExperiences = experiences.map { it.toDomainModel() },
        languages = languages.map { it.toDomainModel() },
        educationBackground = education.map { it.toDomainModel() },
        articles = articles.map { it.toDomainModel() },
    )

    private fun PersonalDataDTO.toDomainModel() = PersonalData(
        name = name,
        description = description,
        location = location,
        photoUrl = photoUrl,
        emailAddress = emailAddress,
        linkedinUrl = linkedinUrl,
        githubUrl = githubUrl,
    )

    private fun IntroductionDTO.toDomainModel() = Introduction(
        title = title,
        description = description,
    )

    private fun SkillDTO.toDomainModel() = Skill(
        description = description,
        imageUrl = imageUrl,
    )

    private fun EducationDTO.toDomainModel() = Education(
        title = title,
        institution = institution,
        period = period,
        location = location,
    )

    private fun CompanyDTO.toDomainModel() = Company(
        name = name,
        period = period,
        location = location,
    )

    private fun RoleDTO.toDomainModel() = Role(
        name = name,
        period = period,
        description = description,
    )

    private fun JobExperienceDTO.toDomainModel() = JobExperience(
        company = company.toDomainModel(),
        roles = roles.map { it.toDomainModel() },
    )

    private fun LanguageDTO.toDomainModel() = Language(
        name = name,
        level = LanguageLevel.fromValue(level),
        description = description,
    )

    private fun ArticleDTO.toDomainModel() = Article(
        title = title,
        description = description,
        imageUrl = imageUrl,
        label = label,
    )
}