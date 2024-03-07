package com.danielleitelima.resume.domain.model

data class CodeSample(
    val title: String,
    val description: String,
    val imageUrl: String,
    val tag: String? = null
)