package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.first

class RollbackToMessage(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(messageId: String, chatId: String){

        val openChat = chatRepository.getOpenChat(chatId).first()

        val selectedMessage = openChat.history.find { it.messageId == messageId }

        val messagesToRollback = openChat.history.filter {
            if (selectedMessage == null) return@filter false
            it.timestamp > selectedMessage.timestamp
        }.map { it.messageId }.toMutableList().apply {
            if (selectedMessage?.isUserSent == true) add(selectedMessage.messageId)
        }

        chatRepository.deleteMessages(chatId, messagesToRollback)
    }
}