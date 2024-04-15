package com.danielleitelima.resume.chat.presentation.route.expression

import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class ExpressionListContract {
    sealed class Event : ViewEvent

    data class State(
        val isLoading: Boolean = false,
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
