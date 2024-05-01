package com.danielleitelima.resume.chat.presentation.screen.article.detail

import com.danielleitelima.resume.chat.domain.Article
import com.danielleitelima.resume.foundation.presentation.foundation.ViewEvent
import com.danielleitelima.resume.foundation.presentation.foundation.ViewSideEffect
import com.danielleitelima.resume.foundation.presentation.foundation.ViewState

sealed class ArticleDetailContract {
    sealed class Event : ViewEvent {
        data class LoadArticle(
            val articleId: String
        ) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val article: Article? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
