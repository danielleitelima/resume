package com.danielleitelima.resume.chat.presentation.screen.creation

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetChats
import kotlinx.coroutines.launch


class ICreationViewModel(
    private val getChats: GetChats
) : CreationViewModel() {

    init {
        viewModelScope.launch {
            val chats = getChats()
            setState {
                copy(
                    chats = chats,
                    isLoading = false
                )
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
