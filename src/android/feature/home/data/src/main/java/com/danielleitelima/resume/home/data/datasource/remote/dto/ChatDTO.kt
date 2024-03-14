package com.danielleitelima.resume.home.data.datasource.remote.dto

data class ChatDTO(
    val id: String,
    val title: String,
    val initialMessageIds: List<String>,
)

data class MessageDTO(
    val id : String,
    val isUserSent: Boolean,
    val message: String,
    val translation: String,
    val sectionAnalysis: List<SectionAnalysisDTO>,
    val expressions: List<ExpressionDTO>,
    val replyOptionsIds: List<String>,
    val relatedArticles: List<AnalysisArticleDTO>
)

data class AnalysisArticleDTO(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val readTime: String,
    val url: String
)

data class SectionAnalysisDTO(
    val id: String,
    val content: String,
    val meaning: MeaningDTO,
    val otherMeanings: List<MeaningDTO>
)

data class MeaningDTO(
    val id: String,
    val content: String,
    val examples: List<ExampleDTO>
)

data class ExpressionDTO(
    val id: String,
    val content: String,
    val description: String,
    val examples: List<ExampleDTO>
)

data class ExampleDTO(
    val id : String,
    val content: String,
    val translation: String
)
