package com.danielleitelima.resume.chat.presentation.route.article.list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class IArticleListViewModel() : ArticleListViewModel() {

    init {
        viewModelScope.launch {
            // TODO: Implement initial loading logic
            setState { copy(isLoading = false) }
        }
    }

    override fun setInitialState(): ArticleListContract.State {
        return ArticleListContract.State()
    }

    override fun handleEvents(event: ArticleListContract.Event) {
    }
}
