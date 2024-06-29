package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.Word
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class GetWord(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(wordId: String): Word {
        return chatRepository.getWord(wordId)
    }
}