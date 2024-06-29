package com.danielleitelima.resume.chat.domain.foundation

import com.danielleitelima.resume.chat.domain.usecase.GetActiveChat
import com.danielleitelima.resume.chat.domain.usecase.GetArticle
import com.danielleitelima.resume.chat.domain.usecase.GetChats
import com.danielleitelima.resume.chat.domain.usecase.GetInitialData
import com.danielleitelima.resume.chat.domain.usecase.GetMessage
import com.danielleitelima.resume.chat.domain.usecase.GetSelectedLanguages
import com.danielleitelima.resume.chat.domain.usecase.GetWord
import com.danielleitelima.resume.chat.domain.usecase.RollbackToMessage
import com.danielleitelima.resume.chat.domain.usecase.SelectMessageOption
import com.danielleitelima.resume.chat.domain.usecase.SetSelectedLanguages
import org.koin.dsl.module

val chatDomainModule = module {
    factory { GetChats(get()) }
    single { GetActiveChat(get()) }
    single { GetInitialData(get()) }
    factory { GetSelectedLanguages(get()) }
    factory { SetSelectedLanguages(get()) }
    factory { GetMessage(get()) }
    factory { GetWord(get()) }
    factory { SelectMessageOption(get()) }
    factory { GetArticle(get()) }
    factory { RollbackToMessage(get()) }
}