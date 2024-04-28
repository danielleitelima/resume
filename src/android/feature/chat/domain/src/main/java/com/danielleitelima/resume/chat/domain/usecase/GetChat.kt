package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChat(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(chatId: String): Flow<Chat> {
        return chatRepository.getById(
            chatId = chatId,
        )
    }
}
