package com.danielleitelima.resume.chat.domain.repository

import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.MessageDetail
import com.danielleitelima.resume.chat.domain.RelatedArticle
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getById(chatId: String): Flow<Chat>

    fun getAll(sync: Boolean = false): Flow<List<Chat>>

    suspend fun getMessageById(messageId: String): MessageDetail

    suspend fun selectMessageOption(chatId: String, messageOptionId: String)

    suspend fun getRelatedArticleById(relatedArticleId: String): RelatedArticle
}
