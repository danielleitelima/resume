package com.danielleitelima.resume.chat.data.datasource.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableLanguagesResponse(
    val data: List<AvailableLanguagesDataResponse>,
)

@Serializable
data class AvailableLanguagesDataResponse(
    val id: String,
    val default: Boolean,
    val code: String,
    val flag: LanguageFlag,
    val translations: List<AvailableLanguagesTranslationResponse>
)

@Serializable
data class LanguageFlag(
    val id: String
)

@Serializable
data class AvailableLanguagesTranslationResponse(
    val id: Int,
    val name: String,
    @SerialName("languages_code")
    val languagesCode: String,
)

@Serializable
data class ChatsResponse(
    val data: List<ChatResponse>,
)

@Serializable
data class ChatResponse(
    val id: String,
    val translations: List<ChatTranslationResponse>,
)

@Serializable
data class ChatTranslationResponse(
    val id: String,
    val title: String,
    @SerialName("languages_code")
    val languagesCode: String,
)

@Serializable
data class ReplyMessageDetailResponse(
    @SerialName("data")
    val data: List<ReplyMessageDetailDataResponse>,
)

@Serializable
data class ReplyMessageDetailDataResponse(
    @SerialName("related_message_id")
    val relatedMessageId: MessageDetailDataResponse,
)

@Serializable
data class InitialMessageOptionsResponse(
    val data: List<InitialMessageOptionsDataResponse>,
)

@Serializable
data class InitialMessageOptionsDataResponse(
    @SerialName("message_id")
    val messageId: MessageOptionResponse,
)

@Serializable
data class MessageOptionsResponse(
    val data: List<MessageOptionsDataResponse>,
)

@Serializable
data class MessageOptionsDataResponse(
    @SerialName("related_message_id")
    val messageId: MessageOptionResponse,
)

@Serializable
data class MessageOptionResponse(
    val id: String,
    @SerialName("user_sent")
    val userSent: Boolean,
    val translations: List<MessageOptionTranslationResponse>,
)

@Serializable
data class MessageOptionTranslationResponse(
    val id: String,
    val content: String,
    @SerialName("languages_code")
    val languagesCode: String,
)

@Serializable
data class MessageDetailResponse(
    val data: List<MessageDetailDataResponse>,
)

@Serializable
data class MessageDetailDataResponse(
    val id: String,
    @SerialName("user_sent")
    val userSent: Boolean,
    val translations: List<MessageDetailTranslationResponse>,
)

@Serializable
data class MessageDetailTranslationResponse(
    val id: String,
    val content: String,
    @SerialName("languages_code")
    val languagesCode: String,
    val sections: List<SectionResponse>,
    val articles: List<ArticleResponse>,
    val expressions: List<ExpressionResponse>,
)

@Serializable
data class ExpressionResponse(
    @SerialName("expression_id")
    val expressionId: ExpressionDataResponse,
)

@Serializable
data class ExpressionDataResponse(
    val id: String,
    val content: String,
    @SerialName("languages_code")
    val languagesCode: String,
    val translations: List<ExpressionTranslationResponse>,
    val examples: List<ExampleResponse>,
)

@Serializable
data class ExpressionTranslationResponse(
    val id: String,
    val description: String,
    @SerialName("languages_code")
    val languagesCode: String,
)

@Serializable
data class ExampleResponse(
    @SerialName("example_id")
    val exampleId: ExampleDataResponse,
)

@Serializable
data class ExampleDataResponse(
    val id: String,
    val translations: List<ExampleTranslationResponse>,
)

@Serializable
data class ExampleTranslationResponse(
    val id: String,
    val content: String,
    @SerialName("languages_code")
    val languagesCode: String,
)

@Serializable
data class ArticleResponse(
    @SerialName("article_id")
    val articleId: ArticleDataResponse,
)

@Serializable
data class ArticleDataResponse(
    val id: String,
    @SerialName("date_created")
    val dateCreated: String,
    @SerialName("date_updated")
    val dateUpdated: String?,
    val readTime: Int,
    val translations: List<ArticleTranslationResponse>,
)

@Serializable
data class ArticleTranslationResponse(
    val id: String,
    val title: String,
    val teaser: String,
    val content: String,
    @SerialName("languages_code")
    val languagesCode: String,
)

@Serializable
data class SectionResponse(
    @SerialName("section_id")
    val sectionId: SectionDataResponse,
)

@Serializable
data class SectionDataResponse(
    val id: String,
    val content: String,
    val position: Int,
    val meanings: List<MeaningResponse>,
)

@Serializable
data class MeaningResponse(
    @SerialName("meaning_id")
    val meaningId: MeaningDataResponse
)

@Serializable
data class MeaningDataResponse(
    val id: String,
    val main: Boolean,
    val translations: List<MeaningTranslationResponse>,
    val examples: List<ExampleResponse>,
)

@Serializable
data class MeaningTranslationResponse(
    val id: String,
    val content: String,
    @SerialName("languages_code")
    val languagesCode: String,
)
