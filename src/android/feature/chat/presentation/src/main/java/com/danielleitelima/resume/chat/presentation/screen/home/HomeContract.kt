package com.danielleitelima.resume.chat.presentation.screen.home

import com.danielleitelima.resume.chat.domain.ActiveChat
import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class HomeContract {
    sealed class Event : ViewEvent

    data class State(
        val isLoading: Boolean = false,
        val activeChats: List<ActiveChat> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
