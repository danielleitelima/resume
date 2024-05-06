package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.MessageDetail
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class GetMessageDetail(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(messageId: String): MessageDetail {
        return chatRepository.getMessageDetail(messageId)
    }
}