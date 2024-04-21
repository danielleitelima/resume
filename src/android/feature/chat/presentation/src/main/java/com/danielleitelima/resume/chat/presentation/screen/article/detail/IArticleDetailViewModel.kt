package com.danielleitelima.resume.chat.presentation.screen.article.detail

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class IArticleDetailViewModel() : ArticleDetailViewModel() {

    init {
        viewModelScope.launch {
            // TODO: Implement initial loading logic
            setState { copy(isLoading = false) }
        }
    }

    override fun setInitialState(): com.danielleitelima.resume.chat.presentation.screen.article.detail.ArticleDetailContract.State {
        return com.danielleitelima.resume.chat.presentation.screen.article.detail.ArticleDetailContract.State()
    }

    override fun handleEvents(event: com.danielleitelima.resume.chat.presentation.screen.article.detail.ArticleDetailContract.Event) {
    }
}
