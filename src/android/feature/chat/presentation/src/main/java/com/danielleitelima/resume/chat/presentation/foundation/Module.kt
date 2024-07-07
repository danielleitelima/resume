package com.danielleitelima.resume.chat.presentation.foundation

import androidx.navigation.NavGraphBuilder
import com.danielleitelima.resume.chat.presentation.screen.creation.CreationScreen
import com.danielleitelima.resume.chat.presentation.screen.creation.CreationViewModel
import com.danielleitelima.resume.chat.presentation.screen.creation.ICreationViewModel
import com.danielleitelima.resume.chat.presentation.screen.home.HomeScreen
import com.danielleitelima.resume.chat.presentation.screen.home.HomeViewModel
import com.danielleitelima.resume.chat.presentation.screen.home.IHomeViewModel
import com.danielleitelima.resume.chat.presentation.screen.message.list.IMessageListViewModel
import com.danielleitelima.resume.chat.presentation.screen.message.list.MessageListScreen
import com.danielleitelima.resume.chat.presentation.screen.message.list.MessageListViewModel
import com.danielleitelima.resume.foundation.presentation.foundation.register
import org.koin.dsl.module

fun NavGraphBuilder.registerChatRoutes() {
    register(HomeScreen)
    register(CreationScreen)
    register(MessageListScreen)
}

val chatPresentationModule = module {
    factory<CreationViewModel> { ICreationViewModel(get()) }
    factory<HomeViewModel> { IHomeViewModel(get(), get()) }
    factory<MessageListViewModel> { IMessageListViewModel(get(), get(), get(), get(),get()) }
}