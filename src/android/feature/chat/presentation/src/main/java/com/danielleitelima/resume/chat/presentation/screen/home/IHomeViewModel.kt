package com.danielleitelima.resume.chat.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetAllActiveChat
import kotlinx.coroutines.launch

class IHomeViewModel(
    private val getAllActiveChat: GetAllActiveChat,
) : HomeViewModel() {

    init {
        viewModelScope.launch {
            getAllActiveChat().collect{
                setState {
                    copy(
                        isLoading = false,
                        activeChats = it
                    )
                }
            }
        }
    }

    override fun setInitialState(): HomeContract.State {
        return HomeContract.State(
            isLoading = true,
        )
    }

    override fun handleEvents(event: HomeContract.Event) {
    }
}
