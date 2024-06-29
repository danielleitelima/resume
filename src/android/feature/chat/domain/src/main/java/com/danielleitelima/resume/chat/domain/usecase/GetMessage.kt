package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.Message
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class GetMessage(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(messageId: String): Message {
        return chatRepository.getMessage(messageId)
    }
}