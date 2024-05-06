package com.danielleitelima.resume.chat.presentation.screen.creation

import com.danielleitelima.resume.chat.domain.Chat
import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class CreationContract {
    sealed class Event : ViewEvent

    data class State(
        val isLoading: Boolean = false,
        val chats: List<Chat> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
