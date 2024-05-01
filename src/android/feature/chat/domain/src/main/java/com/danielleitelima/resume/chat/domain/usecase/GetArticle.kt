package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.Article
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class GetArticle(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(articleId: String): Article {
        return chatRepository.getArticle(
            articleId = articleId
        )
    }
}
