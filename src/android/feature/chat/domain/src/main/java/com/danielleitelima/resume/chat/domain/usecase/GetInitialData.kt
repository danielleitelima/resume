package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.InitialData
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart

class GetInitialData(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): Flow<InitialData> {
        return combine(
            chatRepository.getOpenChats(),
            chatRepository.getTargetLanguages(),
            chatRepository.getTranslationLanguages()
        ) { chats, targetLanguages, translationLanguages ->
            InitialData(
                targetsLanguages = targetLanguages,
                translationLanguages = translationLanguages,
                chats = chats
            )
        }.onStart {
            chatRepository.fetchTranslationLanguages()
            chatRepository.fetchTargetLanguages()
        }
    }
}