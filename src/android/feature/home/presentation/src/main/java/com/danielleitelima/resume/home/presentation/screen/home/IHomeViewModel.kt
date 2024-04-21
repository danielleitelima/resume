package com.danielleitelima.resume.home.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.home.domain.usecase.GetResume
import kotlinx.coroutines.launch

class IHomeViewModel(
    private val getResume: GetResume
) : HomeViewModel() {

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val resume = getResume()
            setState { copy(isLoading = false, resume = resume) }
        }
    }

    override fun setInitialState(): HomeContract.State {
        return HomeContract.State()
    }

    override fun handleEvents(event: HomeContract.Event) {
    }
}
