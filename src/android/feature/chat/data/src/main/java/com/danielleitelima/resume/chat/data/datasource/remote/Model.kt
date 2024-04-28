package com.danielleitelima.resume.chat.data.datasource.remote

data class ChatDTO(
    val id: String,
    val title: String,
    val initialMessageOptionIds: List<String>,
)

data class MessageDTO(
    val id : String,
    val isUserSent: Boolean,
    val content: String,
    val translation: String,
    val sections: List<SectionDTO>,
    val expressions: List<ExpressionDTO>,
    val replyOptionsIds: List<String>,
    val relatedArticles: List<RelatedArticleDTO>
)

data class RelatedArticleDTO(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val readTime: String,
    val url: String
)

data class SectionDTO(
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
