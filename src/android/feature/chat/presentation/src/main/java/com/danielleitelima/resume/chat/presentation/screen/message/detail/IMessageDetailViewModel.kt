package com.danielleitelima.resume.chat.presentation.screen.message.detail

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class IMessageDetailViewModel() : MessageDetailViewModel() {

    init {
        viewModelScope.launch {
            // TODO: Implement initial loading logic
            setState { copy(isLoading = false) }
        }
    }

    override fun setInitialState(): MessageDetailContract.State {
        return MessageDetailContract.State()
    }

    override fun handleEvents(event: MessageDetailContract.Event) {
    }
}
