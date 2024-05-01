package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.SelectedLanguages
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class SetSelectedLanguages(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(selectedLanguages: SelectedLanguages){
        chatRepository.setSelectedTargetLanguage(selectedLanguages.target.code)
        chatRepository.setSelectedTranslationLanguage(selectedLanguages.translation.code)
    }
}