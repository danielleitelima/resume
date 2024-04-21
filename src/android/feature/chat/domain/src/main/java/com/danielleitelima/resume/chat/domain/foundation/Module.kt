package com.danielleitelima.resume.chat.domain.foundation

import com.danielleitelima.resume.chat.domain.usecase.GetAllActiveChat
import com.danielleitelima.resume.chat.domain.usecase.GetAvailableChat
import com.danielleitelima.resume.chat.domain.usecase.GetChat
import com.danielleitelima.resume.chat.domain.usecase.GetMessageDetail
import com.danielleitelima.resume.chat.domain.usecase.GetMessageOptions
import com.danielleitelima.resume.chat.domain.usecase.GetRelatedArticle
import com.danielleitelima.resume.chat.domain.usecase.GetSentMessages
import com.danielleitelima.resume.chat.domain.usecase.SelectMessageOption
import org.koin.dsl.module

val chatDomainModule = module {
    factory { GetAllActiveChat(get()) }
    factory { GetAvailableChat(get()) }
    factory { GetMessageDetail(get()) }
    factory { GetMessageOptions(get()) }
    factory { GetSentMessages(get()) }
    factory { SelectMessageOption(get()) }
    factory { GetChat(get()) }
    factory { GetRelatedArticle(get()) }
}