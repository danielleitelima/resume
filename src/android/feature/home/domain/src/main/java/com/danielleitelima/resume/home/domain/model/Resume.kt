package com.danielleitelima.resume.home.domain.model

data class Resume(
    val personalData: PersonalData,
    val introduction: Introduction,
    val skills: List<Skill>,
    val jobExperiences: List<JobExperience>,
    val languages: List<Language>,
    val educationBackground: List<Education>,
    val articles: List<Article>,
)