package com.danielleitelima.resume.chat.data.datasource.local

data class ChatEntity(
    val id: String,
    val title: String,
    val initialMessageOptionIds: List<String>,
)

data class MessageEntity(
    val id : String,
    val isUserSent: Boolean,
    val content: String,
    val translation: String,
    val section: List<SectionEntity>,
    val expressions: List<ExpressionEntity>,
    val replyOptionsIds: List<String>,
    val relatedArticles: List<RelatedArticleEntity>
)

data class RelatedArticleEntity(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val readTime: String,
    val url: String
)

data class SectionEntity(
    val id: String,
    val content: String,
    val meaning: MeaningEntity,
    val otherMeanings: List<MeaningEntity>
)

data class MeaningEntity(
    val id: String,
    val content: String,
    val examples: List<ExampleEntity>
)

data class ExpressionEntity(
    val id: String,
    val content: String,
    val description: String,
    val examples: List<ExampleEntity>
)

data class ExampleEntity(
    val id : String,
    val content: String,
    val translation: String
)

data class HistoryEntity(
    val chatId: String,
    val messages: List<HistoryMessageEntity>,
)

data class HistoryMessageEntity(
    val id : String,
    val timestamp: Long,
)
