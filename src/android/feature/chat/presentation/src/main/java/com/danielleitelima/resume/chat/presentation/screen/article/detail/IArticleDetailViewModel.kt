package com.danielleitelima.resume.chat.presentation.screen.article.detail

import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.chat.domain.usecase.GetRelatedArticle
import kotlinx.coroutines.launch

class IArticleDetailViewModel(
    val getRelatedArticle: GetRelatedArticle
) : ArticleDetailViewModel() {

    override fun setInitialState(): ArticleDetailContract.State {
        return ArticleDetailContract.State()
    }

    override fun handleEvents(event: ArticleDetailContract.Event) {
        when (event) {
            is ArticleDetailContract.Event.LoadArticle -> {
                viewModelScope.launch {
                    val article = getRelatedArticle(
                        relatedArticleId = event.articleId
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
