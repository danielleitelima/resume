package com.danielleitelima.resume.chat.presentation.foundation

import androidx.navigation.NavGraphBuilder
import com.danielleitelima.resume.chat.presentation.screen.article.detail.ArticleDetailScreen
import com.danielleitelima.resume.chat.presentation.screen.article.detail.ArticleDetailViewModel
import com.danielleitelima.resume.chat.presentation.screen.article.detail.IArticleDetailViewModel
import com.danielleitelima.resume.chat.presentation.screen.article.list.ArticleListScreen
import com.danielleitelima.resume.chat.presentation.screen.article.list.ArticleListViewModel
import com.danielleitelima.resume.chat.presentation.screen.article.list.IArticleListViewModel
import com.danielleitelima.resume.chat.presentation.screen.creation.CreationScreen
import com.danielleitelima.resume.chat.presentation.screen.creation.CreationViewModel
import com.danielleitelima.resume.chat.presentation.screen.creation.ICreationViewModel
import com.danielleitelima.resume.chat.presentation.screen.expression.ExpressionListScreen
import com.danielleitelima.resume.chat.presentation.screen.expression.ExpressionListViewModel
import com.danielleitelima.resume.chat.presentation.screen.expression.IExpressionListViewModel
import com.danielleitelima.resume.chat.presentation.screen.home.HomeScreen
import com.danielleitelima.resume.chat.presentation.screen.home.HomeViewModel
import com.danielleitelima.resume.chat.presentation.screen.home.IHomeViewModel
import com.danielleitelima.resume.chat.presentation.screen.message.detail.IMessageDetailViewModel
import com.danielleitelima.resume.chat.presentation.screen.message.detail.MessageDetailScreen
import com.danielleitelima.resume.chat.presentation.screen.message.detail.MessageDetailViewModel
import com.danielleitelima.resume.chat.presentation.screen.message.list.IMessageListViewModel
import com.danielleitelima.resume.chat.presentation.screen.message.list.MessageListScreen
import com.danielleitelima.resume.chat.presentation.screen.message.list.MessageListViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.register
import org.koin.dsl.module

fun NavGraphBuilder.registerChatRoutes() {
    register(HomeScreen)
    register(ArticleDetailScreen)
    register(ArticleListScreen)
    register(CreationScreen)
    register(ExpressionListScreen)
    register(MessageDetailScreen)
    register(MessageListScreen)
}

val chatPresentationModule = module {
    factory<ArticleDetailViewModel> { IArticleDetailViewModel(get()) }
    factory<ArticleListViewModel> { IArticleListViewModel(get()) }
    factory<CreationViewModel> { ICreationViewModel(get()) }
    factory<ExpressionListViewModel> { IExpressionListViewModel(get()) }
    factory<HomeViewModel> { IHomeViewModel(get(), get()) }
    factory<MessageDetailViewModel> { IMessageDetailViewModel(get()) }
    factory<MessageListViewModel> { IMessageListViewModel(get(), get(), get(), get(),get()) }
}