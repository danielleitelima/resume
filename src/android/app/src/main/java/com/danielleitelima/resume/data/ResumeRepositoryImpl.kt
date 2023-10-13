package com.danielleitelima.resume.data

import com.danielleitelima.resume.datasource.remote.CompanyDTO
import com.danielleitelima.resume.datasource.remote.EducationDTO
import com.danielleitelima.resume.datasource.remote.ExperienceDTO
import com.danielleitelima.resume.datasource.remote.IntroductionDTO
import com.danielleitelima.resume.datasource.remote.LanguageDTO
import com.danielleitelima.resume.datasource.remote.PersonalDataDTO
import com.danielleitelima.resume.datasource.remote.ResumeDTO
import com.danielleitelima.resume.datasource.remote.RoleDTO
import com.danielleitelima.resume.datasource.remote.SkillDTO
import com.danielleitelima.resume.domain.ResumeRepository
import com.danielleitelima.resume.domain.model.Company
import com.danielleitelima.resume.domain.model.Education
import com.danielleitelima.resume.domain.model.Experience
import com.danielleitelima.resume.domain.model.Introduction
import com.danielleitelima.resume.domain.model.Language
import com.danielleitelima.resume.domain.model.LanguageLevel
import com.danielleitelima.resume.domain.model.PersonalData
import com.danielleitelima.resume.domain.model.Resume
import com.danielleitelima.resume.domain.model.Role
import com.danielleitelima.resume.domain.model.Skill

class ResumeRepositoryImpl(): ResumeRepository {

    override suspend fun get(): Resume {
        TODO()
    }

    private fun ResumeDTO.toDomainModel() = Resume(
        personalData = personalData.toDomainModel(),
        introduction = introduction.toDomainModel(),
        skills = skills.map { it.toDomainModel() },
        experiences = experiences.map { it.toDomainModel() },
        languages = languages.map { it.toDomainModel() },
        education = education.map { it.toDomainModel() },
    )

    private fun PersonalDataDTO.toDomainModel() = PersonalData(
        name = name,
        description = description,
        location = location,
        photoUrl = photoUrl,
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

    private fun ExperienceDTO.toDomainModel() = Experience(
        company = company.toDomainModel(),
        roles = roles.map { it.toDomainModel() },
    )

    private fun LanguageDTO.toDomainModel() = Language(
        name = name,
        level = LanguageLevel.fromValue(level),
        description = description,
    )

}