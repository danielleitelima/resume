package com.danielleitelima.resume.chat.domain.repository

import com.danielleitelima.resume.chat.domain.Article
import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.Language
import com.danielleitelima.resume.chat.domain.MessageDetail
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.OpenChat
import com.danielleitelima.resume.chat.domain.Vocabulary
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun fetchChats(): List<Chat>

    fun getOpenChats(): Flow<List<OpenChat>>

    fun getOpenChat(chatId: String): Flow<OpenChat>

    suspend fun addMessage(chatId: String, messageId: String)

    suspend fun deleteMessages(chatId: String, messageIds: List<String>)

    suspend fun fetchInitialMessageOptions(chatId: String): List<MessageOption>

    suspend fun fetchReplyOptions(messageId: String): List<MessageOption>

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
    suspend fun getVocabulary(vocabularyId: String): Vocabulary
    suspend fun fetchWords(ids: List<String>)
}
