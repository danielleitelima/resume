package com.danielleitelima.resume.presentation.activity

import com.danielleitelima.resume.presentation.foundation.ViewEvent
import com.danielleitelima.resume.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.presentation.foundation.ViewState

sealed class MainContract {

    sealed class Event : ViewEvent

    data class State(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
