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
    val vocabularies: List<VocabularyResponse>,
)

@Serializable
data class VocabularyResponse(
    val id: String,
    val content: String,
    val word: String?,
    val lemma: String,
    val beginOffset: Int,
    val partOfSpeech: String,
    val aspect: String,
    val case: String,
    val form: String,
    val gender: String,
    val mood: String,
    val number: String,
    val person: String,
    val tense: String,
    val voice: String,
    val dependency: String?,
    val dependencyType: String?,
)

@Serializable
data class WordsResponse(
    val data: List<WordData>
)

@Serializable
data class WordData(
    val id: String,
    val content: String,
    @SerialName("languages_code")
    val languagesCode: String,
    val meanings: List<WordMeaningData>
)

@Serializable
data class WordMeaningData(
    val id: String,
    val partOfSpeech: String,
    val definitions: List<WordDefinitionData>,
    val relations: List<WordRelatedData>,
    val translations: List<CommonTranslation>,
)

@Serializable
data class WordDefinitionData(
    val id: String,
    val translations: List<CommonTranslation>,
    val examples: List<WordExampleData>
)

@Serializable
data class CommonTranslation(
    val id: String,
    val content: String,
    @SerialName("languages_code")
    val languagesCode: String
)

@Serializable
data class WordRelatedData(
    val id: String,
    val content: String,
    val strong: Boolean,
    val type: String
)

@Serializable
data class WordExampleData(
    val id: String,
    val content: String
)
