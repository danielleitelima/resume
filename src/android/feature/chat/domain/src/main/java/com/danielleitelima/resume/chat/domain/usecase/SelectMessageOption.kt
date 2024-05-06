package com.danielleitelima.resume.chat.domain.usecase

import android.util.Log
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class SelectMessageOption(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(messageOptionId: String, chatId: String){
        chatRepository.fetchMessageDetail(messageOptionId)
        chatRepository.addMessage(messageId = messageOptionId, chatId = chatId)
    }
}