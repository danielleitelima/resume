package com.danielleitelima.resume.chat.presentation.screen.message.list

import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.SentMessage
import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class MessageListContract {
    sealed class Event : ViewEvent{
        data class LoadMessages(val chatId: String) : Event()
        data class SelectMessageOption(val messageId: String) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val sentMessages: List<SentMessage> = emptyList(),
        val messageOptions: List<MessageOption> = emptyList(),
        val chat: Chat? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
