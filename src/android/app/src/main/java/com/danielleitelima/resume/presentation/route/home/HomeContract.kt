package com.danielleitelima.resume.presentation.route.home

import com.danielleitelima.resume.domain.model.Resume
import com.danielleitelima.resume.presentation.foundation.ViewEvent
import com.danielleitelima.resume.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.presentation.foundation.ViewState

sealed class HomeContract {
    sealed class Event : ViewEvent

    data class State(
        val isLoading: Boolean = false,
        val resume: Resume? = null,
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
