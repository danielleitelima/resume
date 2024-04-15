package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.AvailableChat
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAvailableChat(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): Flow<List<AvailableChat>> {
        return chatRepository.getAll(sync = true).map { chats ->
            chats.mapNotNull { chat ->
                if (chat.history.isEmpty()) AvailableChat(
                    id = chat.id,
                    title = chat.title,
                )
                else null
            }
        }
    }
}