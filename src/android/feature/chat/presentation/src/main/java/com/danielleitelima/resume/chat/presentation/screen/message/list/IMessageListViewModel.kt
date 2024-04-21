package com.danielleitelima.resume.chat.presentation.screen.message.list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class IMessageListViewModel() : MessageListViewModel() {

    init {
        viewModelScope.launch {
            // TODO: Implement initial loading logic
            setState { copy(isLoading = false) }
        }
    }

    override fun setInitialState(): MessageListContract.State {
        return MessageListContract.State()
    }

    override fun handleEvents(event: MessageListContract.Event) {
    }
}
