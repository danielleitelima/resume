package com.danielleitelima.resume.chat.presentation.screen.message.detail

import com.danielleitelima.resume.chat.domain.Message
import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class MessageDetailContract {
    sealed class Event : ViewEvent {
        data class LoadMessageDetail(
            val messageId: String
        ) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val messageDetail: Message? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
