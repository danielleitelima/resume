package com.danielleitelima.resume.home.presentation.screen.home

import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState
import com.danielleitelima.resume.home.domain.model.Resume

sealed class HomeContract {
    sealed class Event : ViewEvent

    data class State(
        val isLoading: Boolean = false,
        val resume: Resume? = null,
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
