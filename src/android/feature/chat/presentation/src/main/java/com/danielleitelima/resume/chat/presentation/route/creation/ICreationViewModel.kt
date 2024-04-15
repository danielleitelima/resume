package com.danielleitelima.resume.chat.presentation.route.creation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ICreationViewModel() : CreationViewModel() {

    init {
        viewModelScope.launch {
            // TODO: Implement initial loading logic
            setState { copy(isLoading = false) }
        }
    }

    override fun setInitialState(): CreationContract.State {
        return CreationContract.State()
    }

    override fun handleEvents(event: CreationContract.Event) {
    }
}
