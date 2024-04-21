package com.danielleitelima.resume.chat.presentation.screen.message.list

import com.danielleitelima.resume.foundation.presentation.foundation.BaseViewModel

abstract class MessageListViewModel :
    BaseViewModel<MessageListContract.Event, MessageListContract.State, MessageListContract.Effect>()
