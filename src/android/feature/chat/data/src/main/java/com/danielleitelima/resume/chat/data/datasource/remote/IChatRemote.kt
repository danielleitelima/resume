package com.danielleitelima.resume.chat.data.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class IChatRemote(
    private val client: HttpClient
): ChatRemote {

    companion object{
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
            append("related_message_id.translations.sections.section_id.id")
            append(",")
            append("related_message_id.translations.sections.section_id.content")
            append(",")
            append("related_message_id.translations.sections.section_id.position")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.id")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.main")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.translations.id")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.translations.languages_code")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.translations.content")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.examples.example_id.id")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.examples.example_id.translations.id")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.examples.example_id.translations.languages_code")
            append(",")
            append("related_message_id.translations.sections.section_id.meanings.meaning_id.examples.example_id.translations.content")
            append(",")
            append("related_message_id.translations.expressions.expression_id.id")
            append(",")
            append("related_message_id.translations.expressions.expression_id.content")
            append(",")
            append("related_message_id.translations.expressions.expression_id.languages_code")
            append(",")
            append("related_message_id.translations.expressions.expression_id.translations.id")
            append(",")
            append("related_message_id.translations.expressions.expression_id.translations.description")
            append(",")
            append("related_message_id.translations.expressions.expression_id.translations.languages_code")
            append(",")
            append("related_message_id.translations.expressions.expression_id.examples.example_id.id")
            append(",")
            append("related_message_id.translations.expressions.expression_id.examples.example_id.translations.id")
            append(",")
            append("related_message_id.translations.expressions.expression_id.examples.example_id.translations.languages_code")
            append(",")
            append("related_message_id.translations.expressions.expression_id.examples.example_id.translations.content")
            append(",")
            append("related_message_id.translations.expressions.expression_id.translations.articles.article_id")
            append(",")
            append("related_message_id.translations.articles.article_id.id")
            append(",")
            append("related_message_id.translations.articles.article_id.date_created")
            append(",")
            append("related_message_id.translations.articles.article_id.date_updated")
            append(",")
            append("related_message_id.translations.articles.article_id.readTime")
            append(",")
            append("related_message_id.translations.articles.article_id.translations.id")
            append(",")
            append("related_message_id.translations.articles.article_id.translations.languages_code")
            append(",")
            append("related_message_id.translations.articles.article_id.translations.title")
            append(",")
            append("related_message_id.translations.articles.article_id.translations.teaser")
            append(",")
            append("related_message_id.translations.articles.article_id.translations.content")
            append("&")
            append("filter[message_id][_eq]=")
            append(messageId)
            append("&")
            append("limit=1")
        }
        return client.get(url).call.body<ReplyMessageDetailResponse>()
    }

    override suspend fun getMessageDetail(id: String): MessageDetailResponse {
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
            append("translations.sections.section_id.id")
            append(",")
            append("translations.sections.section_id.content")
            append(",")
            append("translations.sections.section_id.position")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.id")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.main")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.translations.id")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.translations.languages_code")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.translations.content")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.examples.example_id.id")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.examples.example_id.translations.id")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.examples.example_id.translations.languages_code")
            append(",")
            append("translations.sections.section_id.meanings.meaning_id.examples.example_id.translations.content")
            append(",")
            append("translations.expressions.expression_id.id")
            append(",")
            append("translations.expressions.expression_id.content")
            append(",")
            append("translations.expressions.expression_id.languages_code")
            append(",")
            append("translations.expressions.expression_id.translations.id")
            append(",")
            append("translations.expressions.expression_id.translations.description")
            append(",")
            append("translations.expressions.expression_id.translations.languages_code")
            append(",")
            append("translations.expressions.expression_id.examples.example_id.id")
            append(",")
            append("translations.expressions.expression_id.examples.example_id.translations.id")
            append(",")
            append("translations.expressions.expression_id.examples.example_id.translations.languages_code")
            append(",")
            append("translations.expressions.expression_id.examples.example_id.translations.content")
            append(",")
            append("translations.expressions.expression_id.translations.articles.article_id")
            append(",")
            append("translations.articles.article_id.id")
            append(",")
            append("translations.articles.article_id.date_created")
            append(",")
            append("translations.articles.article_id.date_updated")
            append(",")
            append("translations.articles.article_id.readTime")
            append(",")
            append("translations.articles.article_id.translations.id")
            append(",")
            append("translations.articles.article_id.translations.languages_code")
            append(",")
            append("translations.articles.article_id.translations.title")
            append(",")
            append("translations.articles.article_id.translations.teaser")
            append(",")
            append("translations.articles.article_id.translations.content")
            append("&")
            append("filter[id][_eq]=")
            append(id)
        }
        return client.get(url).call.body<MessageDetailResponse>()
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