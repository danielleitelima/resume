package com.danielleitelima.resume.chat.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.SelectedLanguages
import com.danielleitelima.resume.chat.domain.usecase.GetInitialData
import com.danielleitelima.resume.chat.domain.usecase.SetSelectedLanguages
import kotlinx.coroutines.launch

class IHomeViewModel(
    private val getInitialData: GetInitialData,
    private val setSelectedLanguages: SetSelectedLanguages
) : HomeViewModel() {

    init {
        viewModelScope.launch {
            getInitialData().collect {
                setState {
                    copy(
                        isLoading = false,
                        chats = it.chats,
                        targetLanguages = it.targetsLanguages,
                        translationLanguages = it.translationLanguages,
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
        when (event) {
            is HomeContract.Event.OnLanguagesSelected -> {
                viewModelScope.launch {
                    setSelectedLanguages(
                        SelectedLanguages(
                            target = event.target,
                            translation = event.translation
                        )
                    )
                }
            }
        }
    }
}
