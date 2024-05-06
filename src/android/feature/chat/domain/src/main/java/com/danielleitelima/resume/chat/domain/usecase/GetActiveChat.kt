package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.ActiveChat
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetActiveChat(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(chatId: String): Flow<ActiveChat> {
        return chatRepository.getOpenChat(chatId).map { openChat ->

            val activeChat: ActiveChat

            val lastMessage = openChat.history.maxByOrNull { it.timestamp }

            if (lastMessage == null) {
                val messageOptions = chatRepository.fetchInitialMessageOptions(chatId)

                activeChat = ActiveChat(
                    openChat = openChat,
                    currentMessageOptions = messageOptions,
                )

                return@map activeChat
            }

            if (lastMessage.isUserSent) {
                val firstReplyDetail = chatRepository.fetchFirstReplyDetail(lastMessage.messageId)

                chatRepository.addMessage(
                    chatId = chatId,
                    messageId = firstReplyDetail.id,
                )

                activeChat = ActiveChat(
                    openChat = openChat,
                    currentMessageOptions = null,
                )

                return@map activeChat
            }

            val replyOptions = chatRepository.fetchReplyOptions(lastMessage.messageId)

            if (replyOptions.any { it.isUserSent.not() }) {
                val firstNonUserReply = replyOptions.first { it.isUserSent.not() }

                chatRepository.addMessage(chatId, firstNonUserReply.id)

                activeChat = ActiveChat(
                    openChat = openChat,
                    currentMessageOptions = null,
                )

                return@map activeChat
            }

            val messageOptions = chatRepository.fetchReplyOptions(lastMessage.messageId)

            activeChat = ActiveChat(
                openChat = openChat,
                currentMessageOptions = messageOptions,
            )

            return@map activeChat
        }
    }
}