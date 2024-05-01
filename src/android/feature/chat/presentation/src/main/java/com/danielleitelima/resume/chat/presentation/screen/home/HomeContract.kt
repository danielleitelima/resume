package com.danielleitelima.resume.chat.presentation.screen.home

import com.danielleitelima.resume.chat.domain.Language
import com.danielleitelima.resume.chat.domain.OpenChat
import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class HomeContract {
    sealed class Event : ViewEvent{
        data class OnLanguagesSelected(
            val target: Language,
            val translation: Language
        ) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val chats: List<OpenChat> = emptyList(),
        val targetLanguages: List<Language> = emptyList(),
        val translationLanguages: List<Language> = emptyList()
    ) : ViewState {
        val selectedTargetLanguage by lazy {
            targetLanguages.firstOrNull{
                it.selected
            }
        }

        val selectedTranslationLanguage by lazy {
            translationLanguages.firstOrNull{
                it.selected
            }
        }
    }

    sealed class Effect : ViewSideEffect
}
