package com.danielleitelima.resume.chat.presentation.route.expression

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class IExpressionListViewModel() : ExpressionListViewModel() {

    init {
        viewModelScope.launch {
            // TODO: Implement initial loading logic
            setState { copy(isLoading = false) }
        }
    }

    override fun setInitialState(): ExpressionListContract.State {
        return ExpressionListContract.State()
    }

    override fun handleEvents(event: ExpressionListContract.Event) {
    }
}
