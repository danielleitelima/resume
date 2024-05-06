package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.SelectedLanguages
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class GetSelectedLanguages(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): SelectedLanguages {
        val target = chatRepository.getSelectedTargetLanguage()
        val translation = chatRepository.getSelectedTranslationLanguage()

        return SelectedLanguages(
            target = target,
            translation = translation
        )
    }
}