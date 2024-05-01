package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.first

class RollbackToMessage(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(messageId: String, chatId: String){

        val openChat = chatRepository.getOpenChat(chatId).first()

        val selectedMessage = openChat.history.find { it.id == messageId }

        val messagesToRollback = openChat.history.filter {
            if (selectedMessage == null) return@filter false
            it.timestamp > selectedMessage.timestamp
        }.map { it.id }.toMutableList().apply {
            if (selectedMessage?.isUserSent == true) add(selectedMessage.id)
        }

        chatRepository.deleteMessages(chatId, messagesToRollback)
    }
}