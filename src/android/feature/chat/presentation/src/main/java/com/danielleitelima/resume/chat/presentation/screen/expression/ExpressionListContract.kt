package com.danielleitelima.resume.chat.presentation.screen.expression

import com.danielleitelima.resume.chat.domain.Expression
import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class ExpressionListContract {
    sealed class Event : ViewEvent {
        data class LoadExpressions(
            val messageId: String
        ) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val expressions: List<Expression> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
