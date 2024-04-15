package com.danielleitelima.resume.chat.presentation.foundation

import androidx.navigation.NavGraphBuilder
import com.danielleitelima.resume.chat.presentation.route.article.detail.ArticleDetailRoute
import com.danielleitelima.resume.chat.presentation.route.article.detail.ArticleDetailViewModel
import com.danielleitelima.resume.chat.presentation.route.article.detail.IArticleDetailViewModel
import com.danielleitelima.resume.chat.presentation.route.article.list.ArticleListRoute
import com.danielleitelima.resume.chat.presentation.route.article.list.ArticleListViewModel
import com.danielleitelima.resume.chat.presentation.route.article.list.IArticleListViewModel
import com.danielleitelima.resume.chat.presentation.route.creation.CreationRoute
import com.danielleitelima.resume.chat.presentation.route.creation.CreationViewModel
import com.danielleitelima.resume.chat.presentation.route.creation.ICreationViewModel
import com.danielleitelima.resume.chat.presentation.route.expression.ExpressionListRoute
import com.danielleitelima.resume.chat.presentation.route.expression.ExpressionListViewModel
import com.danielleitelima.resume.chat.presentation.route.expression.IExpressionListViewModel
import com.danielleitelima.resume.chat.presentation.route.home.HomeRoute
import com.danielleitelima.resume.chat.presentation.route.home.HomeViewModel
import com.danielleitelima.resume.chat.presentation.route.home.IHomeViewModel
import com.danielleitelima.resume.chat.presentation.route.message.detail.IMessageDetailViewModel
import com.danielleitelima.resume.chat.presentation.route.message.detail.MessageDetailRoute
import com.danielleitelima.resume.chat.presentation.route.message.detail.MessageDetailViewModel
import com.danielleitelima.resume.chat.presentation.route.message.list.IMessageListViewModel
import com.danielleitelima.resume.chat.presentation.route.message.list.MessageListRoute
import com.danielleitelima.resume.chat.presentation.route.message.list.MessageListViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.navigation.register
import org.koin.dsl.module

fun NavGraphBuilder.registerChatRoutes() {
    register(HomeRoute)
    register(CreationRoute)
    register(MessageListRoute)
    register(MessageDetailRoute)
    register(ExpressionListRoute)
    register(ArticleListRoute)
    register(ArticleDetailRoute)
}

val chatPresentationModule = module {
    factory<ArticleDetailViewModel> { IArticleDetailViewModel() }
    factory<ArticleListViewModel> { IArticleListViewModel() }
    factory<CreationViewModel> { ICreationViewModel() }
    factory<ExpressionListViewModel> { IExpressionListViewModel() }
    factory<HomeViewModel> { IHomeViewModel() }
    factory<MessageDetailViewModel> { IMessageDetailViewModel() }
    factory<MessageListViewModel> { IMessageListViewModel() }
}