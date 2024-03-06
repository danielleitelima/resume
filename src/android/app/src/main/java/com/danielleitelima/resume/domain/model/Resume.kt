package com.danielleitelima.resume.domain.model

data class Resume(
    val personalData: PersonalData,
    val introduction: Introduction,
    val skills: List<Skill>,
    val experiences: List<Experience>,
    val languages: List<Language>,
    val education: List<Education>
)