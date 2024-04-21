package com.danielleitelima.resume.chat.presentation.screen.creation

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetAvailableChat
import kotlinx.coroutines.launch


class ICreationViewModel(
    private val getAvailableChat: GetAvailableChat
) : CreationViewModel() {

    init {
        viewModelScope.launch {
            getAvailableChat().collect{
                setState {
                    copy(
                        availableChats = it,
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun setInitialState(): CreationContract.State {
        return CreationContract.State(
            isLoading = true
        )
    }

    override fun handleEvents(event: CreationContract.Event) {
    }
}
