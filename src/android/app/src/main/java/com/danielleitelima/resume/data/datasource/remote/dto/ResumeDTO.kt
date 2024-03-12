package com.danielleitelima.resume.data.datasource.remote.dto

data class ResumeDTO(
    val personalData: PersonalDataDTO,
    val introduction: IntroductionDTO,
    val skills: List<SkillDTO>,
    val experiences: List<JobExperienceDTO>,
    val languages: List<LanguageDTO>,
    val education: List<EducationDTO>,
    val articles: List<AnalysisArticleDTO>
)

data class PersonalDataDTO(
    val name: String,
    val description: String,
    val location: String,
    val photoUrl: String,
    val emailAddress: String,
    val linkedinUrl: String
)

data class IntroductionDTO(
    val title: String,
    val description: String
)

data class SkillDTO(
    val description: String,
    val imageUrl: String
)

data class JobExperienceDTO(
    val company: CompanyDTO,
    val roles: List<RoleDTO>
)

data class CompanyDTO(
    val name: String,
    val period: String,
    val location: String
)

data class RoleDTO(
    val name: String,
    val period: String,
    val description: String
)

data class LanguageDTO(
    val name: String,
    val level: Int,
    val description: String
)

data class EducationDTO(
    val title: String,
    val institution: String,
    val period: String,
    val location: String
)

data class ArticleDTO(
    val title: String,
    val description: String,
    val imageUrl: String,
    val label: String,
)
