package com.danielleitelima.resume.chat.data.foundation

import com.danielleitelima.resume.chat.data.repository.FakeChatRepository
import com.danielleitelima.resume.chat.domain.repository.ChatRepository
import org.koin.dsl.module

val chatDataModule = module {
    single<ChatRepository> { FakeChatRepository() }
}
