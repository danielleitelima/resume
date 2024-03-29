package com.danielleitelima.resume.home.domain.model

data class PersonalData(
    val name: String,
    val description: String,
    val location: String,
    val photoUrl: String,
    val emailAddress: String,
    val linkedinUrl: String,
    val githubUrl: String
)