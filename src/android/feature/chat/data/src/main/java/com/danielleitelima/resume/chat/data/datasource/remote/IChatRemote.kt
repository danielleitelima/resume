package com.danielleitelima.resume.chat.data.datasource.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class IChatRemote(
    private val client: HttpClient,
) : ChatRemote {

    companion object {
        private const val BASE_URL = "https://directus.danielleitelima.com"
    }

    override suspend fun getTargetLanguages(): AvailableLanguagesResponse {
        val url = buildString {
            append("$BASE_URL/items/target_language")
            append("?")
            append("fields=")
            append("id")
            append(",")
            append("default")
            append(",")
            append("code")
            append(",")
            append("translations.id")
            append(",")
            append("translations.name")
            append(",")
            append("translations.languages_code")
            append(",")
            append("flag.id")
        }
        return client.get(url).call.body<AvailableLanguagesResponse>()
    }

    override suspend fun getTranslationLanguages(): AvailableLanguagesResponse {
        val url = buildString {
            append("$BASE_URL/items/translation_language")
            append("?")
            append("fields=")
            append("id")
            append(",")
            append("default")
            append(",")
            append("code")
            append(",")
            append("translations.id")
            append(",")
            append("translations.name")
            append(",")
            append("translations.languages_code")
            append(",")
            append("flag.id")
        }

        return client.get(url).call.body<AvailableLanguagesResponse>()
    }

    override suspend fun getChats(): ChatsResponse {
        val url = buildString {
            append("$BASE_URL/items/chat")
            append("?")
            append("fields=")
            append("id")
            append(",")
            append("translations.id")
            append(",")
            append("translations.title")
            append(",")
            append("translations.languages_code")
        }

        return client.get(url).call.body<ChatsResponse>()
    }

    override suspend fun getInitialMessageOptions(chatId: String): InitialMessageOptionsResponse {
        val url = buildString {
            append("$BASE_URL/items/chat_message")
            append("?")
            append("fields=")
            append("message_id.id")
            append(",")
            append("message_id.user_sent")
            append(",")
            append("message_id.translations.id")
            append(",")
            append("message_id.translations.content")
            append(",")
            append("message_id.translations.languages_code")
            append("&")
            append("filter[chat_id][_eq]=")
            append(chatId)
        }

        return client.get(url).call.body<InitialMessageOptionsResponse>()
    }

    override suspend fun getReplyOptions(messageId: String): MessageOptionsResponse {
        val url = buildString {
            append("$BASE_URL/items/message_message")
            append("?")
            append("fields=")
            append("related_message_id.id")
            append(",")
            append("related_message_id.user_sent")
            append(",")
            append("related_message_id.translations.id")
            append(",")
            append("related_message_id.translations.content")
            append(",")
            append("related_message_id.translations.languages_code")
            append("&")
            append("filter[message_id][_eq]=")
            append(messageId)
        }

        return client.get(url).call.body<MessageOptionsResponse>()
    }

    override suspend fun getFirstReplyOptionDetail(messageId: String): ReplyMessageDetailResponse {
        val url = buildString {
            append("$BASE_URL/items/message_message")
            append("?")
            append("fields=")
            append("related_message_id.id")
            append(",")
            append("related_message_id.user_sent")
            append(",")
            append("related_message_id.translations.id")
            append(",")
            append("related_message_id.translations.content")
            append(",")
            append("related_message_id.translations.languages_code")
            append(",")
            append("related_message_id.translations.vocabularies.id")
            append(",")
            append("related_message_id.translations.vocabularies.content")
            append(",")
            append("related_message_id.translations.vocabularies.partOfSpeech")
            append(",")
            append("related_message_id.translations.vocabularies.aspect")
            append(",")
            append("related_message_id.translations.vocabularies.case")
            append(",")
            append("related_message_id.translations.vocabularies.form")
            append(",")
            append("related_message_id.translations.vocabularies.lemma")
            append(",")
            append("related_message_id.translations.vocabularies.beginOffset")
            append(",")
            append("related_message_id.translations.vocabularies.gender")
            append(",")
            append("related_message_id.translations.vocabularies.mood")
            append(",")
            append("related_message_id.translations.vocabularies.number")
            append(",")
            append("related_message_id.translations.vocabularies.person")
            append(",")
            append("related_message_id.translations.vocabularies.reciprocity")
            append(",")
            append("related_message_id.translations.vocabularies.tense")
            append(",")
            append("related_message_id.translations.vocabularies.voice")
            append(",")
            append("related_message_id.translations.vocabularies.dependencyType")
            append(",")
            append("related_message_id.translations.vocabularies.dependency")
            append(",")
            append("related_message_id.translations.vocabularies.word")
            append("&")
            append("filter[message_id][_eq]=")
            append(messageId)
            append("&")
            append("limit=1")
        }

        Log.d("IChatRemote", "url: $url")
        Log.d("IChatRemote", "messageId: $messageId")
        Log.d("IChatRemote", "getFirstReplyOptionDetail: ${client.get(url).call.body<String>()}")

        return client.get(url).call.body<ReplyMessageDetailResponse>()
    }

    override suspend fun getMessageDetail(messageId: String): MessageDetailResponse {
        val url = buildString {
            append("$BASE_URL/items/message")
            append("?")
            append("fields=")
            append("id")
            append(",")
            append("user_sent")
            append(",")
            append("translations.id")
            append(",")
            append("translations.content")
            append(",")
            append("translations.languages_code")
            append(",")
            append("translations.vocabularies.id")
            append(",")
            append("translations.vocabularies.content")
            append(",")
            append("translations.vocabularies.partOfSpeech")
            append(",")
            append("translations.vocabularies.aspect")
            append(",")
            append("translations.vocabularies.case")
            append(",")
            append("translations.vocabularies.form")
            append(",")
            append("translations.vocabularies.lemma")
            append(",")
            append("translations.vocabularies.beginOffset")
            append(",")
            append("translations.vocabularies.gender")
            append(",")
            append("translations.vocabularies.mood")
            append(",")
            append("translations.vocabularies.number")
            append(",")
            append("translations.vocabularies.person")
            append(",")
            append("translations.vocabularies.reciprocity")
            append(",")
            append("translations.vocabularies.tense")
            append(",")
            append("translations.vocabularies.voice")
            append(",")
            append("translations.vocabularies.dependencyType")
            append(",")
            append("translations.vocabularies.dependency")
            append(",")
            append("translations.vocabularies.word")
            append("&")
            append("filter[id][_eq]=")
            append(messageId)
        }

        Log.d("IChatRemote", "url: $url")
        Log.d("IChatRemote", "messageId: $messageId")
        Log.d("IChatRemote", "getMessageDetail: ${client.get(url).call.body<String>()}")

        return client.get(url).call.body<MessageDetailResponse>()
    }

    override suspend fun getWords(ids: List<String>): WordsResponse {
        val idList = ids.joinToString(",") { it }

        val url = buildString {
            append("$BASE_URL/items/word")
            append("?fields=")
            append("id")
            append(",")
            append("content")
            append(",")
            append("languages_code")
            append(",")
            append("meanings.id")
            append(",")
            append("meanings.translations.id")
            append(",")
            append("meanings.translations.content")
            append(",")
            append("meanings.translations.languages_code")
            append(",")
            append("meanings.partOfSpeech")
            append(",")
            append("meanings.definitions.id")
            append(",")
            append("meanings.definitions.translations.id")
            append(",")
            append("meanings.definitions.translations.content")
            append(",")
            append("meanings.definitions.translations.languages_code")
            append(",")
            append("meanings.definitions.examples.id")
            append(",")
            append("meanings.definitions.examples.content")
            append(",")
            append("meanings.relations.id")
            append(",")
            append("meanings.relations.content")
            append(",")
            append("meanings.relations.strong")
            append(",")
            append("meanings.relations.type")
            append("&")
            append("filter[id][_in]=")
            append(ids.joinToString(",") { it })
        }

        Log.d("IChatRemote", "url: $url")
        Log.d("IChatRemote", "getWordIds: $idList")
        Log.d("IChatRemote", "getWords: ${client.get(url).call.body<String>()}")

        return client.get(url).call.body<WordsResponse>()
    }

    override fun getImageUrl(resourceId: String): String {
        return "$BASE_URL/assets/$resourceId"
    }
}

fun main() {
    runBlocking {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }

        val chatAPI = IChatRemote(client)
        val response = chatAPI.getTranslationLanguages()
        println(response)
    }
}