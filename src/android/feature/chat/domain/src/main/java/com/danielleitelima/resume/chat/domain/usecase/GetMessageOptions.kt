package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMessageOptions(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(chatId: String): Flow<List<MessageOption>> {
       return chatRepository.getById(chatId).map { chat ->
            if(chat.history.isEmpty()) {
                chat.initialMessageOptions
            } else {
                chat.history.last().replyOptionsIds.map { messageOptionId ->
                    val messageDetail = chatRepository.getMessageById(messageOptionId)

                    MessageOption(
                        id = messageDetail.id,
                        content = messageDetail.content
                    )
                }
            }
        }
    }
}