package com.danielleitelima.resume.chat.presentation.route.article.list

import com.danielleitelima.resume.foundation.presentation.foundation.BaseViewModel

abstract class ArticleListViewModel :
    BaseViewModel<ArticleListContract.Event, ArticleListContract.State, ArticleListContract.Effect>()
