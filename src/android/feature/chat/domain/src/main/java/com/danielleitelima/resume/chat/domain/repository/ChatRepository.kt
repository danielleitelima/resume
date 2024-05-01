package com.danielleitelima.resume.chat.domain.repository

import com.danielleitelima.resume.chat.domain.Article
import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.Language
import com.danielleitelima.resume.chat.domain.MessageDetail
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.OpenChat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    /*
    * Get chats available remotely.
    * */
    suspend fun fetchChats(): List<Chat>

    /*
    * Get chats with a message history locally saved.
    * */
    fun getOpenChats(): Flow<List<OpenChat>>

    /*
    * Observes an open chat by its id.
    * */
    fun getOpenChat(chatId: String): Flow<OpenChat>

    /*
    * Gets the available message options for an empty chat from the remote API.
    * Nothing gets saved locally, a back-end call is triggered every time the user opens the dialog.
    * */
    suspend fun addMessage(chatId: String, messageId: String)


    suspend fun deleteMessages(chatId: String, messageIds: List<String>)

    /*
    * Gets the available message options for an empty chat from the remote API.
    * Nothing gets saved locally, a back-end call is triggered every time the user opens the dialog.
    * */
    suspend fun fetchInitialMessageOptions(chatId: String): List<MessageOption>

    /*
    * Gets the available reply options from a message.
    * Nothing gets saved locally, a back-end call is triggered every time the user opens the dialog.
    * */
    suspend fun fetchReplyOptions(messageId: String): List<MessageOption>

    /*
    * Gets the message detail from the remote API and caches the data when a message gets chosen.
    * When the user selects a message this function is triggered by the use case
    * and then the chat history is updated, also by the use case.
    * If the data is already cached, the cached data is returned.
    * This happens when the user navigated to the message detail screen.
    * */
    suspend fun fetchMessageDetail(messageId: String)

    suspend fun fetchFirstReplyDetail(messageId: String): MessageDetail

    suspend fun getMessageDetail(messageId: String): MessageDetail

    suspend fun getArticle(articleId: String): Article

    suspend fun fetchTargetLanguages(): List<Language>

    suspend fun fetchTranslationLanguages(): List<Language>

    fun getTargetLanguages(): Flow<List<Language>>

    fun getTranslationLanguages(): Flow<List<Language>>

    fun getSelectedTargetLanguage(): Language

    fun getSelectedTranslationLanguage(): Language

    fun setSelectedTargetLanguage(languageId: String)

    fun setSelectedTranslationLanguage(languageId: String)
}
