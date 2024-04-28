package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.ActiveChat
import com.danielleitelima.resume.chat.domain.SentMessage
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllActiveChat(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): Flow<List<ActiveChat>> {
        return chatRepository.getAll(sync = true).map { chats ->
            chats.mapNotNull { chat ->
                if (chat.history.isEmpty()) null
                else ActiveChat(
                    id = chat.id,
                    title = chat.title,
                    lastSentMessage = chat.history.maxByOrNull { it.timestamp }.let { lastMessage ->
                        lastMessage ?: return@let null
                        SentMessage(
                            id = lastMessage.id,
                            content = lastMessage.content,
                            isUserSent = lastMessage.isUserSent,
                            timestamp = lastMessage.timestamp
                        )
                    }
                )
            }
        }
    }
}