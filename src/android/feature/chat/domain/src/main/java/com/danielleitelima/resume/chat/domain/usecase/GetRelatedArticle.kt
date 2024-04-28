package com.danielleitelima.resume.chat.domain.usecase

import com.danielleitelima.resume.chat.domain.RelatedArticle
import com.danielleitelima.resume.chat.domain.repository.ChatRepository

class GetRelatedArticle(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(relatedArticleId: String): RelatedArticle {
        return chatRepository.getRelatedArticleById(
            relatedArticleId = relatedArticleId
        )
    }
}
