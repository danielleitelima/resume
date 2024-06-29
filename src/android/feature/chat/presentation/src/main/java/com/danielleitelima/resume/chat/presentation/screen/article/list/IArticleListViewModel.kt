package com.danielleitelima.resume.chat.presentation.screen.article.list

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetMessage
import kotlinx.coroutines.launch


class IArticleListViewModel(
    val getMessageDetail: GetMessage
) : ArticleListViewModel() {
    override fun setInitialState(): ArticleListContract.State {
        return ArticleListContract.State()
    }

    override fun handleEvents(event: ArticleListContract.Event) {
        when (event) {
            is ArticleListContract.Event.LoadArticles -> {
                viewModelScope.launch {
                    val messageDetail = getMessageDetail(
                        messageId = event.messageId
                    )

                    setState {
                        copy(
                            articles = messageDetail.articles
                        )
                    }
                }
            }
        }
    }
}
