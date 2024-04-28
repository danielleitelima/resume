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
            chats.map { chat ->
                AvailableChat(
                    id = chat.id,
                    title = chat.title,
                    hasHistory = chat.history.isNotEmpty()
                )
            }
        }
    }
}