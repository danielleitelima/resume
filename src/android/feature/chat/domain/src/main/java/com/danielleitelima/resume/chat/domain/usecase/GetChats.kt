package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class GetChats(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): List<Chat> {
        return chatRepository.fetchChats()
    }
}