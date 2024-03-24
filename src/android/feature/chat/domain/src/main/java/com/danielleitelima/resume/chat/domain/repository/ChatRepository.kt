package com.danielleitelima.resume.chat.domain.repository

import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.MessageDetail
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getById(chatId: String): Flow<Chat>

    fun getAll(sync: Boolean = false): Flow<List<Chat>>

    suspend fun getMessageById(chatId: String, messageId: String): MessageDetail
}
