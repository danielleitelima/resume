package com.danielleitelima.resume.domain.model

data class Language(
    val name: String,
    val level: LanguageLevel,
    val description: String,
//    TODO: Remove this field
    val color: ULong = 0x000000u,
)