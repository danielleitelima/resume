package com.danielleitelima.resume.chat.presentation.screen.article.detail

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetArticle
import kotlinx.coroutines.launch

class IArticleDetailViewModel(
    val getArticle: GetArticle
) : ArticleDetailViewModel() {

    override fun setInitialState(): ArticleDetailContract.State {
        return ArticleDetailContract.State()
    }

    override fun handleEvents(event: ArticleDetailContract.Event) {
        when (event) {
            is ArticleDetailContract.Event.LoadArticle -> {
                viewModelScope.launch {
                    val article = getArticle(
                        articleId = event.articleId
                    )

                    setState {
                        copy(
                            article = article
                        )
                    }
                }
            }
        }
    }
}
