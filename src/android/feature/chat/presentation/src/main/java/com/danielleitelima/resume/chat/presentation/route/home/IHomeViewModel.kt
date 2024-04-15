package com.danielleitelima.resume.chat.presentation.route.home

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetAllActiveChat
import kotlinx.coroutines.launch

class IHomeViewModel(
    private val getAllActiveChat: GetAllActiveChat,
) : HomeViewModel() {

    init {
        viewModelScope.launch {
            // TODO: Implement initial loading logic
            setState { copy(isLoading = false) }
        }
    }

    override fun setInitialState(): HomeContract.State {
        return HomeContract.State()
    }

    override fun handleEvents(event: HomeContract.Event) {
    }
}
