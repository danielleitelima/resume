package com.danielleitelima.resume.chat.presentation.screen.message.list

import com.danielleitelima.resume.chat.domain.Message
import com.danielleitelima.resume.chat.domain.MessageOption
import com.danielleitelima.resume.chat.domain.OpenChat
import com.danielleitelima.resume.chat.domain.Word
import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class MessageListContract {
    sealed class Event : ViewEvent{
        data class LoadChat(val chatId: String) : Event()
        data class SelectMessageOption(val messageId: String) : Event()
        data class RollbackToMessage(val messageId: String) : Event()
        data class SelectMessage(val messageId: String) : Event()
        data class SelectWord(val wordId: String? = null) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val isTyping: Boolean = false,
        val openChat: OpenChat? = null,
        val messageOptions: List<MessageOption>? = null,
        val selectedMessage: Message? = null,
        val selectedWord: Word? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
