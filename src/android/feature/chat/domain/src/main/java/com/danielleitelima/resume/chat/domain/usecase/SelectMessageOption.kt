package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class SelectMessageOption(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String, messageOptionId: String){
        return chatRepository.selectMessageOption(
            chatId = chatId,
            messageOptionId = messageOptionId
        )
    }
}
