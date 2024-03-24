package com.danielleitelima.resume.chat.domain.domain

import com.danielleitelima.resume.chat.domain.SentMessage
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSentMessages(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(chatId: String): Flow<List<SentMessage>> {
        return chatRepository.getById(chatId = chatId).map { chat ->
            chat.history.map { messageDetail ->
                SentMessage(
                    id = messageDetail.id,
                    content = messageDetail.content,
                    timestamp = messageDetail.timestamp
                )
            }
        }
    }
}