package com.danielleitelima.resume.chat.presentation.screen.article.detail

import com.danielleitelima.resume.foundation.presentation.foundation.BaseViewModel

abstract class ArticleDetailViewModel :
    BaseViewModel<ArticleDetailContract.Event, ArticleDetailContract.State, ArticleDetailContract.Effect>()
